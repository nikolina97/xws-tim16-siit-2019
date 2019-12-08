//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.07 at 07:54:28 PM CET 
//


package com.xws.application.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.github_com.nikolina97.xws_tim16_siit_2019 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Author_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "author");
    private final static QName _Chapter_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "chapter");
    private final static QName _Paragraph_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "paragraph");
    private final static QName _Reference_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "reference");
    private final static QName _Reviewer_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "reviewer");
    private final static QName _TParagraphUnderlineItalic_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "italic");
    private final static QName _TParagraphUnderlineBold_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "bold");
    private final static QName _TParagraphUnderline_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "underline");
    private final static QName _TParagraphRef_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "ref");
    private final static QName _TParagraphImageName_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "image_name");
    private final static QName _TParagraphTableName_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "table_name");
    private final static QName _CoverLetterSignatureDepartment_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "department");
    private final static QName _CoverLetterSignatureUniversityName_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "university_name");
    private final static QName _CoverLetterSignatureName_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "name");
    private final static QName _CoverLetterSignatureDegree_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "degree");
    private final static QName _TReferenceArticleLink_QNAME = new QName("https://github.com/nikolina97/xws-tim16-siit-2019", "link");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.github_com.nikolina97.xws_tim16_siit_2019
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CoverLetter }
     * 
     */
    public CoverLetter createCoverLetter() {
        return new CoverLetter();
    }

    /**
     * Create an instance of {@link Review }
     * 
     */
    public Review createReview() {
        return new Review();
    }

    /**
     * Create an instance of {@link ScientificPaper }
     * 
     */
    public ScientificPaper createScientificPaper() {
        return new ScientificPaper();
    }

    /**
     * Create an instance of {@link TQuestion }
     * 
     */
    public TQuestion createTQuestion() {
        return new TQuestion();
    }

    /**
     * Create an instance of {@link TQuestion.Answer }
     * 
     */
    public TQuestion.Answer createTQuestionAnswer() {
        return new TQuestion.Answer();
    }

    /**
     * Create an instance of {@link ScientificPaper.Abstract }
     * 
     */
    public ScientificPaper.Abstract createScientificPaperAbstract() {
        return new ScientificPaper.Abstract();
    }

    /**
     * Create an instance of {@link TPerson }
     * 
     */
    public TPerson createTPerson() {
        return new TPerson();
    }

    /**
     * Create an instance of {@link TCoverLetterAuthor }
     * 
     */
    public TCoverLetterAuthor createTCoverLetterAuthor() {
        return new TCoverLetterAuthor();
    }

    /**
     * Create an instance of {@link TParagraph }
     * 
     */
    public TParagraph createTParagraph() {
        return new TParagraph();
    }

    /**
     * Create an instance of {@link TParagraph.Underline }
     * 
     */
    public TParagraph.Underline createTParagraphUnderline() {
        return new TParagraph.Underline();
    }

    /**
     * Create an instance of {@link TParagraph.Italic }
     * 
     */
    public TParagraph.Italic createTParagraphItalic() {
        return new TParagraph.Italic();
    }

    /**
     * Create an instance of {@link TParagraph.Bold }
     * 
     */
    public TParagraph.Bold createTParagraphBold() {
        return new TParagraph.Bold();
    }

    /**
     * Create an instance of {@link TChapter }
     * 
     */
    public TChapter createTChapter() {
        return new TChapter();
    }

    /**
     * Create an instance of {@link TReference }
     * 
     */
    public TReference createTReference() {
        return new TReference();
    }

    /**
     * Create an instance of {@link TReference.Article }
     * 
     */
    public TReference.Article createTReferenceArticle() {
        return new TReference.Article();
    }

    /**
     * Create an instance of {@link TEditor }
     * 
     */
    public TEditor createTEditor() {
        return new TEditor();
    }

    /**
     * Create an instance of {@link CoverLetter.Reviewers }
     * 
     */
    public CoverLetter.Reviewers createCoverLetterReviewers() {
        return new CoverLetter.Reviewers();
    }

    /**
     * Create an instance of {@link CoverLetter.Signature }
     * 
     */
    public CoverLetter.Signature createCoverLetterSignature() {
        return new CoverLetter.Signature();
    }

    /**
     * Create an instance of {@link Review.Reviewers }
     * 
     */
    public Review.Reviewers createReviewReviewers() {
        return new Review.Reviewers();
    }

    /**
     * Create an instance of {@link Review.Authors }
     * 
     */
    public Review.Authors createReviewAuthors() {
        return new Review.Authors();
    }

    /**
     * Create an instance of {@link Review.Questions }
     * 
     */
    public Review.Questions createReviewQuestions() {
        return new Review.Questions();
    }

    /**
     * Create an instance of {@link Review.Comments }
     * 
     */
    public Review.Comments createReviewComments() {
        return new Review.Comments();
    }

    /**
     * Create an instance of {@link ScientificPaper.Authors }
     * 
     */
    public ScientificPaper.Authors createScientificPaperAuthors() {
        return new ScientificPaper.Authors();
    }

    /**
     * Create an instance of {@link ScientificPaper.Chapters }
     * 
     */
    public ScientificPaper.Chapters createScientificPaperChapters() {
        return new ScientificPaper.Chapters();
    }

    /**
     * Create an instance of {@link ScientificPaper.References }
     * 
     */
    public ScientificPaper.References createScientificPaperReferences() {
        return new ScientificPaper.References();
    }

    /**
     * Create an instance of {@link TTableRow }
     * 
     */
    public TTableRow createTTableRow() {
        return new TTableRow();
    }

    /**
     * Create an instance of {@link TQuestion.Answer.Image }
     * 
     */
    public TQuestion.Answer.Image createTQuestionAnswerImage() {
        return new TQuestion.Answer.Image();
    }

    /**
     * Create an instance of {@link TQuestion.Answer.Table }
     * 
     */
    public TQuestion.Answer.Table createTQuestionAnswerTable() {
        return new TQuestion.Answer.Table();
    }

    /**
     * Create an instance of {@link ScientificPaper.Abstract.Keywords }
     * 
     */
    public ScientificPaper.Abstract.Keywords createScientificPaperAbstractKeywords() {
        return new ScientificPaper.Abstract.Keywords();
    }

    /**
     * Create an instance of {@link TPerson.University }
     * 
     */
    public TPerson.University createTPersonUniversity() {
        return new TPerson.University();
    }

    /**
     * Create an instance of {@link TCoverLetterAuthor.University }
     * 
     */
    public TCoverLetterAuthor.University createTCoverLetterAuthorUniversity() {
        return new TCoverLetterAuthor.University();
    }

    /**
     * Create an instance of {@link TParagraph.Underline.Bold }
     * 
     */
    public TParagraph.Underline.Bold createTParagraphUnderlineBold() {
        return new TParagraph.Underline.Bold();
    }

    /**
     * Create an instance of {@link TParagraph.Underline.Italic }
     * 
     */
    public TParagraph.Underline.Italic createTParagraphUnderlineItalic() {
        return new TParagraph.Underline.Italic();
    }

    /**
     * Create an instance of {@link TParagraph.Italic.Bold }
     * 
     */
    public TParagraph.Italic.Bold createTParagraphItalicBold() {
        return new TParagraph.Italic.Bold();
    }

    /**
     * Create an instance of {@link TParagraph.Italic.Underline }
     * 
     */
    public TParagraph.Italic.Underline createTParagraphItalicUnderline() {
        return new TParagraph.Italic.Underline();
    }

    /**
     * Create an instance of {@link TParagraph.Bold.Italic }
     * 
     */
    public TParagraph.Bold.Italic createTParagraphBoldItalic() {
        return new TParagraph.Bold.Italic();
    }

    /**
     * Create an instance of {@link TParagraph.Bold.Underline }
     * 
     */
    public TParagraph.Bold.Underline createTParagraphBoldUnderline() {
        return new TParagraph.Bold.Underline();
    }

    /**
     * Create an instance of {@link TChapter.Image }
     * 
     */
    public TChapter.Image createTChapterImage() {
        return new TChapter.Image();
    }

    /**
     * Create an instance of {@link TChapter.Table }
     * 
     */
    public TChapter.Table createTChapterTable() {
        return new TChapter.Table();
    }

    /**
     * Create an instance of {@link TReference.Article.Link }
     * 
     */
    public TReference.Article.Link createTReferenceArticleLink() {
        return new TReference.Article.Link();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "author")
    public JAXBElement<TPerson> createAuthor(TPerson value) {
        return new JAXBElement<TPerson>(_Author_QNAME, TPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TChapter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "chapter")
    public JAXBElement<TChapter> createChapter(TChapter value) {
        return new JAXBElement<TChapter>(_Chapter_QNAME, TChapter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "paragraph")
    public JAXBElement<TParagraph> createParagraph(TParagraph value) {
        return new JAXBElement<TParagraph>(_Paragraph_QNAME, TParagraph.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TReference }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "reference")
    public JAXBElement<TReference> createReference(TReference value) {
        return new JAXBElement<TReference>(_Reference_QNAME, TReference.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "reviewer")
    public JAXBElement<TPerson> createReviewer(TPerson value) {
        return new JAXBElement<TPerson>(_Reviewer_QNAME, TPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Underline.Italic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "italic", scope = TParagraph.Underline.class)
    public JAXBElement<TParagraph.Underline.Italic> createTParagraphUnderlineItalic(TParagraph.Underline.Italic value) {
        return new JAXBElement<TParagraph.Underline.Italic>(_TParagraphUnderlineItalic_QNAME, TParagraph.Underline.Italic.class, TParagraph.Underline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Underline.Bold }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "bold", scope = TParagraph.Underline.class)
    public JAXBElement<TParagraph.Underline.Bold> createTParagraphUnderlineBold(TParagraph.Underline.Bold value) {
        return new JAXBElement<TParagraph.Underline.Bold>(_TParagraphUnderlineBold_QNAME, TParagraph.Underline.Bold.class, TParagraph.Underline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Underline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "underline", scope = TParagraph.class)
    public JAXBElement<TParagraph.Underline> createTParagraphUnderline(TParagraph.Underline value) {
        return new JAXBElement<TParagraph.Underline>(_TParagraphUnderline_QNAME, TParagraph.Underline.class, TParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "ref", scope = TParagraph.class)
    public JAXBElement<String> createTParagraphRef(String value) {
        return new JAXBElement<String>(_TParagraphRef_QNAME, String.class, TParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "image_name", scope = TParagraph.class)
    public JAXBElement<String> createTParagraphImageName(String value) {
        return new JAXBElement<String>(_TParagraphImageName_QNAME, String.class, TParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Italic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "italic", scope = TParagraph.class)
    public JAXBElement<TParagraph.Italic> createTParagraphItalic(TParagraph.Italic value) {
        return new JAXBElement<TParagraph.Italic>(_TParagraphUnderlineItalic_QNAME, TParagraph.Italic.class, TParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "table_name", scope = TParagraph.class)
    public JAXBElement<String> createTParagraphTableName(String value) {
        return new JAXBElement<String>(_TParagraphTableName_QNAME, String.class, TParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Bold }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "bold", scope = TParagraph.class)
    public JAXBElement<TParagraph.Bold> createTParagraphBold(TParagraph.Bold value) {
        return new JAXBElement<TParagraph.Bold>(_TParagraphUnderlineBold_QNAME, TParagraph.Bold.class, TParagraph.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "bold", scope = TParagraph.Underline.Italic.class)
    public JAXBElement<String> createTParagraphUnderlineItalicBold(String value) {
        return new JAXBElement<String>(_TParagraphUnderlineBold_QNAME, String.class, TParagraph.Underline.Italic.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Bold.Underline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "underline", scope = TParagraph.Bold.class)
    public JAXBElement<TParagraph.Bold.Underline> createTParagraphBoldUnderline(TParagraph.Bold.Underline value) {
        return new JAXBElement<TParagraph.Bold.Underline>(_TParagraphUnderline_QNAME, TParagraph.Bold.Underline.class, TParagraph.Bold.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Bold.Italic }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "italic", scope = TParagraph.Bold.class)
    public JAXBElement<TParagraph.Bold.Italic> createTParagraphBoldItalic(TParagraph.Bold.Italic value) {
        return new JAXBElement<TParagraph.Bold.Italic>(_TParagraphUnderlineItalic_QNAME, TParagraph.Bold.Italic.class, TParagraph.Bold.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "department", scope = CoverLetter.Signature.class)
    public JAXBElement<String> createCoverLetterSignatureDepartment(String value) {
        return new JAXBElement<String>(_CoverLetterSignatureDepartment_QNAME, String.class, CoverLetter.Signature.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "university_name", scope = CoverLetter.Signature.class)
    public JAXBElement<String> createCoverLetterSignatureUniversityName(String value) {
        return new JAXBElement<String>(_CoverLetterSignatureUniversityName_QNAME, String.class, CoverLetter.Signature.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "name", scope = CoverLetter.Signature.class)
    public JAXBElement<String> createCoverLetterSignatureName(String value) {
        return new JAXBElement<String>(_CoverLetterSignatureName_QNAME, String.class, CoverLetter.Signature.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "degree", scope = CoverLetter.Signature.class)
    public JAXBElement<String> createCoverLetterSignatureDegree(String value) {
        return new JAXBElement<String>(_CoverLetterSignatureDegree_QNAME, String.class, CoverLetter.Signature.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "italic", scope = TParagraph.Underline.Bold.class)
    public JAXBElement<String> createTParagraphUnderlineBoldItalic(String value) {
        return new JAXBElement<String>(_TParagraphUnderlineItalic_QNAME, String.class, TParagraph.Underline.Bold.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "bold", scope = TParagraph.Italic.Underline.class)
    public JAXBElement<String> createTParagraphItalicUnderlineBold(String value) {
        return new JAXBElement<String>(_TParagraphUnderlineBold_QNAME, String.class, TParagraph.Italic.Underline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "italic", scope = TParagraph.Bold.Underline.class)
    public JAXBElement<String> createTParagraphBoldUnderlineItalic(String value) {
        return new JAXBElement<String>(_TParagraphUnderlineItalic_QNAME, String.class, TParagraph.Bold.Underline.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Italic.Underline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "underline", scope = TParagraph.Italic.class)
    public JAXBElement<TParagraph.Italic.Underline> createTParagraphItalicUnderline(TParagraph.Italic.Underline value) {
        return new JAXBElement<TParagraph.Italic.Underline>(_TParagraphUnderline_QNAME, TParagraph.Italic.Underline.class, TParagraph.Italic.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TParagraph.Italic.Bold }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "bold", scope = TParagraph.Italic.class)
    public JAXBElement<TParagraph.Italic.Bold> createTParagraphItalicBold(TParagraph.Italic.Bold value) {
        return new JAXBElement<TParagraph.Italic.Bold>(_TParagraphUnderlineBold_QNAME, TParagraph.Italic.Bold.class, TParagraph.Italic.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "underline", scope = TParagraph.Bold.Italic.class)
    public JAXBElement<String> createTParagraphBoldItalicUnderline(String value) {
        return new JAXBElement<String>(_TParagraphUnderline_QNAME, String.class, TParagraph.Bold.Italic.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TReference.Article.Link }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "link", scope = TReference.Article.class)
    public JAXBElement<TReference.Article.Link> createTReferenceArticleLink(TReference.Article.Link value) {
        return new JAXBElement<TReference.Article.Link>(_TReferenceArticleLink_QNAME, TReference.Article.Link.class, TReference.Article.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://github.com/nikolina97/xws-tim16-siit-2019", name = "underline", scope = TParagraph.Italic.Bold.class)
    public JAXBElement<String> createTParagraphItalicBoldUnderline(String value) {
        return new JAXBElement<String>(_TParagraphUnderline_QNAME, String.class, TParagraph.Italic.Bold.class, value);
    }

}
