package com.dtstack.legacy;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface WsdlSettings {
    public final static String CACHE_WSDLS = WsdlSettings.class.getSimpleName() + "@" + "cache-wsdls";

    public final static String XML_GENERATION_TYPE_EXAMPLE_VALUE = WsdlSettings.class.getSimpleName() + "@"
            + "xml-generation-type-example-value";

    public final static String XML_GENERATION_TYPE_COMMENT_TYPE = WsdlSettings.class.getSimpleName() + "@"
            + "xml-generation-type-comment-type";

    public final static String XML_GENERATION_ALWAYS_INCLUDE_OPTIONAL_ELEMENTS = WsdlSettings.class.getSimpleName()
            + "@" + "xml-generation-always-include-optional-elements";

    public final static String PRETTY_PRINT_RESPONSE_MESSAGES = WsdlSettings.class.getSimpleName() + "@"
            + "pretty-print-response-xml";

    public final static String ATTACHMENT_PARTS = WsdlSettings.class.getSimpleName() + "@" + "attachment-parts";

    public final static String ALLOW_INCORRECT_CONTENTTYPE = WsdlSettings.class.getSimpleName() + "@"
            + "allow-incorrect-contenttype";

    public final static String ENABLE_MTOM = WsdlSettings.class.getSimpleName() + "@" + "enable-mtom";

    public static final String SCHEMA_DIRECTORY = WsdlSettings.class.getSimpleName() + "@" + "schema-directory";

    public final static String NAME_WITH_BINDING = WsdlSettings.class.getSimpleName() + "@" + "name-with-binding";

    public final static String EXCLUDED_TYPES = WsdlSettings.class.getSimpleName() + "@" + "excluded-types";

    public final static String STRICT_SCHEMA_TYPES = WsdlSettings.class.getSimpleName() + "@" + "strict-schema-types";

    public final static String COMPRESSION_LIMIT = WsdlSettings.class.getSimpleName() + "@" + "compression-limit";

    public final static String PRETTY_PRINT_PROJECT_FILES = WsdlSettings.class.getSimpleName() + "@"
            + "pretty-print-project-files";

    public static final String XML_GENERATION_SKIP_COMMENTS = WsdlSettings.class.getSimpleName() + "@"
            + "xml-generation-skip-comments";

    public final static String TRIM_WSDL = WsdlSettings.class.getSimpleName() + "@"
            + "trim-wsdl";
}
