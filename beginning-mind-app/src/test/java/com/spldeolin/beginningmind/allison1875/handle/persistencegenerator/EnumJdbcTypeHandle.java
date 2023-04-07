package com.spldeolin.beginningmind.allison1875.handle.persistencegenerator;

import static com.github.javaparser.StaticJavaParser.parseAnnotation;
import static com.github.javaparser.utils.CodeGenerationUtils.f;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.math.NumberUtils;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.JavadocBlockTag.Type;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.base.ast.AstForest;
import com.spldeolin.allison1875.base.constant.BaseConstant;
import com.spldeolin.allison1875.base.exception.QualifierAbsentException;
import com.spldeolin.allison1875.base.util.MoreStringUtils;
import com.spldeolin.allison1875.base.util.ast.Saves;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.persistencegenerator.facade.javabean.JavaTypeNamingDto;
import com.spldeolin.allison1875.persistencegenerator.handle.JdbcTypeHandle;
import com.spldeolin.allison1875.persistencegenerator.javabean.InformationSchemaDto;
import com.spldeolin.beginningmind.allison1875.config.EnumConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2021-05-24
 */
@Slf4j
@Singleton
public class EnumJdbcTypeHandle extends JdbcTypeHandle {

    @Inject
    private EnumConfig enumConfig;

    @Inject
    private PersistenceGeneratorConfig persistenceGeneratorConfig;

    private static final Collection<String> enumImports = Lists.newArrayList("java.util.Arrays",
            "com.fasterxml.jackson.annotation.JsonCreator", "com.fasterxml.jackson.annotation.JsonValue",
            "com.spldeolin.beginningmind.ancestor.EnumAncestor", "lombok.AllArgsConstructor", "lombok.Getter");

    @Override
    public JavaTypeNamingDto jdbcType2javaType(InformationSchemaDto columnMeta, AstForest astForest) {
        JavaTypeNamingDto javaTypeNamingDto = super.jdbcType2javaType(columnMeta, astForest);

        String description = columnMeta.getColumnComment();

        Pattern enumPattern = Pattern.compile("E\\((.+?)\\)");
        Matcher enumMatcher = enumPattern.matcher(description);
        if (enumMatcher.find() && javaTypeNamingDto.getQualifier().equals(String.class.getName())) {
            String enumName = MoreStringUtils.underscoreToUpperCamel(columnMeta.getTableName())
                    + MoreStringUtils.underscoreToUpperCamel(columnMeta.getColumnName()) + "Enum";

            CompilationUnit cu = new CompilationUnit();
            cu.setPackageDeclaration(enumConfig.getEnumPackage());
            enumImports.forEach(cu::addImport);
            EnumDeclaration ed = new EnumDeclaration();
            Javadoc classJavadoc = new JavadocComment(
                    enumMatcher.replaceFirst("").trim() + Strings.repeat(BaseConstant.NEW_LINE, 2) + "<p><p>"
                            + "<strong>该枚举" + BaseConstant.BY_ALLISON_1875 + "</strong>").parse();
            classJavadoc.getBlockTags().add(new JavadocBlockTag(Type.AUTHOR,
                    persistenceGeneratorConfig.getAuthor() + " " + LocalDate.now()));
            ed.setJavadocComment(classJavadoc);
            ed.addAnnotation(parseAnnotation("@Getter"));
            ed.addAnnotation(parseAnnotation("@AllArgsConstructor"));
            ed.setPublic(true);
            ed.setName(enumName);
            ed.addImplementedType("EnumAncestor<String>");
            for (String part : enumMatcher.group(1).split(" ")) {
                String[] split = part.split("=");
                String enumConstantName = split[0];
                if (NumberUtils.isCreatable(enumConstantName)) {
                    enumConstantName = "e" + enumConstantName;
                }
                EnumConstantDeclaration ecd = new EnumConstantDeclaration().setName(enumConstantName)
                        .addArgument(new StringLiteralExpr(split[0])).addArgument(new StringLiteralExpr(split[1]));
                ed.addEntry(ecd);
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
            ed.addMember(StaticJavaParser.parseBodyDeclaration(
                    "@Override public String toString() { return asJavabean().toString(); }").asMethodDeclaration());
            cu.addType(ed);
            Path enumPath = CodeGenerationUtils.fileInPackageAbsolutePath(astForest.getPrimaryJavaRoot(),
                    enumConfig.getEnumPackage(), enumName + ".java");
            cu.setStorage(enumPath);
            Saves.add(cu);

            return new JavaTypeNamingDto().setSimpleName(ed.getNameAsString())
                    .setQualifier(ed.getFullyQualifiedName().orElseThrow(QualifierAbsentException::new));
        }

        Pattern typePattern = Pattern.compile("T\\((.+?)\\)");
        Matcher typeMatcher = typePattern.matcher(description);
        if (typeMatcher.find()) {
            String qualifier = typeMatcher.group(1);
            return new JavaTypeNamingDto().setSimpleName(qualifier.substring(qualifier.lastIndexOf('.') + 1))
                    .setQualifier(qualifier);
        }

        return javaTypeNamingDto;
    }

}