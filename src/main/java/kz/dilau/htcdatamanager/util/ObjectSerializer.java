package kz.dilau.htcdatamanager.util;

import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ObjectSerializer {
    public static String serialize(Object obj) {
        StringWriter result = new StringWriter();

        JAXB.marshal(obj, result);

        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String str, Class<T> requiredClass) {
        try {
            JAXBContext payloadContext = JAXBContext.newInstance(requiredClass);
            if (requiredClass.getClass().getAnnotation(XmlRootElement.class) != null) {
                return (T) payloadContext.createUnmarshaller().unmarshal(new StringReader(str));
            } else {
                Source source = new StreamSource(new StringReader(str));
                Unmarshaller u = payloadContext.createUnmarshaller();
                return u.unmarshal(source, requiredClass).getValue();
            }
        } catch (JAXBException e) {
            throw new IllegalArgumentException(String.format("Could not parse %s from string\n%s\n%s", requiredClass.getName(), str, e.getMessage()), e);
        }
    }

    public static Map<String, Object> introspect(Object obj) {
        Map<String, Object> result = new HashMap<>();
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    if (!pd.getName().equals("class")) {
                        result.put(pd.getName(), reader.invoke(obj));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
