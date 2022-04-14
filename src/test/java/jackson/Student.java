package jackson;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
        public String name;
        public String surname;
        public Info Info;
    }
