package com.spldeolin.beginningmind.allison1875.handle.persistencegenerator;

import static com.github.javaparser.StaticJavaParser.parseAnnotation;
import static com.github.javaparser.utils.CodeGenerationUtils.f;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.JavadocBlockTag.Type;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.base.constant.BaseConstant;
import com.spldeolin.allison1875.base.creator.CuCreator;
import com.spldeolin.allison1875.base.util.ast.Imports;
import com.spldeolin.allison1875.base.util.ast.JavadocDescriptions;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.persistencegenerator.handle.GenerateEntityFieldHandle;
import com.spldeolin.allison1875.persistencegenerator.handle.GenerateQueryDesignFieldHandle;
import com.spldeolin.allison1875.persistencegenerator.javabean.PropertyDto;
import com.spldeolin.beginningmind.allison1875.config.EnumConfig;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2020-11-14
 */
@Singleton
@Log4j2
public class DesignatedEntityTypeOverwriteHandle implements GenerateEntityFieldHandle, GenerateQueryDesignFieldHandle {

    @Inject
    private EnumConfig enumConfig;

    @Inject
    private PersistenceGeneratorConfig persistenceGeneratorConfig;

    @Override
    public Collection<CompilationUnit> handleEntityField(PropertyDto propertyDto, FieldDeclaration field,
            Path sourceRoot) {
        Collection<CompilationUnit> result = Lists.newArrayList();
        String description = propertyDto.getDescription();
        Pattern enumPattern = Pattern.compile("E\\((.+?)\\)");
        Matcher enumMatcher = enumPattern.matcher(description);
        if (enumMatcher.find() && propertyDto.getJavaType() == String.class) {
            String enumName = StringUtils.capitalize(propertyDto.getPropertyName()) + "Enum";
            Path enumPath = CodeGenerationUtils
                    .fileInPackageAbsolutePath(sourceRoot, enumConfig.getEnumPackage(), enumName + ".java");

            List<JavadocBlockTag> authorTags = Lists.newArrayList();
            Set<String> originalConstants = Sets.newTreeSet();
            if (enumPath.toFile().exists()) {
                try {
                    CompilationUnit cu = StaticJavaParser.parse(enumPath);
                    for (EnumConstantDeclaration ecd : cu.findAll(EnumConstantDeclaration.class)) {
                        originalConstants.add(ecd.toString());
                    }
                    this.getAuthorTags(authorTags, cu);
                } catch (Exception e) {
                    log.warn("StaticJavaParser.parse failed enumPath={}", enumPath, e);
                }
                log.info("Enum文件已存在，覆盖它。 [{}]", enumPath);
            } else {
                authorTags.add(new JavadocBlockTag(Type.AUTHOR,
                        persistenceGeneratorConfig.getAuthor() + " " + LocalDate.now()));
            }

            Set<String> destinationConstants = Sets.newTreeSet();

            // 生成Enum（可能是覆盖）
            ArrayList<String> imports = Lists
                    .newArrayList("java.util.Arrays", "com.fasterxml.jackson.annotation.JsonCreator",
                            "com.fasterxml.jackson.annotation.JsonValue",
                            "com.spldeolin.beginningmind.ancestor.EnumAncestor", "lombok.AllArgsConstructor",
                            "lombok.Getter");
            CuCreator enumCreator = new CuCreator(sourceRoot, enumConfig.getEnumPackage(), imports, () -> {
                EnumDeclaration ed = new EnumDeclaration();
                Javadoc classJavadoc = new JavadocComment(
                        enumMatcher.replaceFirst("").trim() + Strings.repeat(BaseConstant.NEW_LINE, 2) + "<p><p>"
                                + "<strong>该枚举" + BaseConstant.BY_ALLISON_1875 + "</strong>").parse();
                classJavadoc.getBlockTags().addAll(authorTags);
                ed.setJavadocComment(classJavadoc);
                ed.addAnnotation(parseAnnotation("@Getter"));
                ed.addAnnotation(parseAnnotation("@AllArgsConstructor"));
                ed.setPublic(true);
                ed.setName(enumName);
                ed.addImplementedType("EnumAncestor<String>");
                for (String part : enumMatcher.group(1).split(" ")) {
                    String[] split = part.split("=");
                    EnumConstantDeclaration ecd = new EnumConstantDeclaration().setName(split[0])
                            .addArgument(new StringLiteralExpr(split[0])).addArgument(new StringLiteralExpr(split[1]));
                    ed.addEntry(ecd);
                    destinationConstants.add(ecd.toString());
                }
                ed.addMember(StaticJavaParser.parseBodyDeclaration("@JsonValue private final String code;"));
                ed.addMember(StaticJavaParser.parseBodyDeclaration("private final String title;"));
                ed.addMember(StaticJavaParser.parseBodyDeclaration(
                        "public static boolean valid(String code) { return Arrays.stream(values()).anyMatch"
                                + "(anEnum -> anEnum.getCode().equals(code)); }").asMethodDeclaration()
                        .setJavadocComment("判断参数code是否是一个有效的枚举"));
                ed.addMember(StaticJavaParser.parseBodyDeclaration(
                        f("@JsonCreator public static %s of(String code) { return Arrays"
                                + ".stream(values()).filter(anEnum -> anEnum.getCode().equals(code))"
                                + ".findFirst().orElse(null); }", enumName)).asMethodDeclaration()
                        .setJavadocComment("获取code对应的枚举"));
                ed.addMember(StaticJavaParser
                        .parseBodyDeclaration("@Override public String toString() { return asJavabean().toString(); }")
                        .asMethodDeclaration());
                return ed;
            });
            result.add(enumCreator.create(false));

            reportDiff(originalConstants, destinationConstants, enumCreator.getPrimaryTypeQualifier());

            Imports.ensureImported(field, enumCreator.getPrimaryTypeQualifier());
            com.github.javaparser.ast.type.Type newType = StaticJavaParser.parseType(enumCreator.getPrimaryTypeName());
            FieldDeclaration clone = field.clone();
            clone.removeJavaDocComment();
            log.info("{} 的Field的类型替换为 {}", clone, newType);
            field.setAllTypes(newType);
            field.getJavadoc().ifPresent(javadoc -> field.setJavadocComment(new JavadocComment(
                    enumPattern.matcher(JavadocDescriptions.getRaw(javadoc)).replaceFirst("").trim())));
        }

        Pattern typePattern = Pattern.compile("T\\((.+?)\\)");
        Matcher typeMatcher = typePattern.matcher(description);
        if (typeMatcher.find()) {
            String qualifier = typeMatcher.group(1);
            Imports.ensureImported(field, qualifier);
            com.github.javaparser.ast.type.Type newType = StaticJavaParser
                    .parseType(qualifier.substring(qualifier.lastIndexOf('.') + 1));
            FieldDeclaration clone = field.clone();
            clone.removeJavaDocComment();
            log.info("{} 的Field的类型替换为 {}", clone, newType);
            field.setAllTypes(newType);
            field.getJavadoc().ifPresent(javadoc -> field.setJavadocComment(new JavadocComment(
                    typePattern.matcher(JavadocDescriptions.getRaw(javadoc)).replaceFirst("").trim())));
        }
        return result;
    }

    @Override
    public Collection<CompilationUnit> handlerQueryDesignField(PropertyDto propertyDto, FieldDeclaration field,
            Path sourceRoot) {
        String firstTypeArgument = field.getCommonType().asClassOrInterfaceType().getTypeArguments()
                .orElseThrow(RuntimeException::new).get(0).toString();

        Collection<CompilationUnit> result = handleEntityField(propertyDto, field, sourceRoot);
        String type = field.getCommonType().toString();
        if (!type.contains("QueryPredicate<" + firstTypeArgument)) {
            field.setAllTypes(
                    StaticJavaParser.parseType(String.format("QueryPredicate<%s, %s>", firstTypeArgument, type)));
        }
        return result;
    }

    private void reportDiff(Set<String> originalConstants, Set<String> destinationConstants, String enumQualifier) {
        if (originalConstants.isEmpty()) {
            return;
        }
        SetView<String> adds = Sets.difference(destinationConstants, originalConstants);
        if (adds.size() > 0) {
            log.info("{} 中将会新增枚举项 {}", enumQualifier, Joiner.on(", ").join(adds));
        }
        SetView<String> deletes = Sets.difference(originalConstants, destinationConstants);
        if (deletes.size() > 0) {
            log.info("{} 中将会删除枚举项 {}", enumQualifier, Joiner.on(", ").join(deletes));
        }
    }

    private void getAuthorTags(List<JavadocBlockTag> authorTags, CompilationUnit cu) {
        cu.getPrimaryType().flatMap(NodeWithJavadoc::getJavadoc)
                .ifPresent(javadoc -> javadoc.getBlockTags().forEach(javadocTag -> {
                    if (javadocTag.getType() == Type.AUTHOR) {
                        authorTags.add(javadocTag);
                    }
                }));
    }

}