package de.btu.openinfra.backend.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.pojos.LocalizedString;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.PtLocalePojo;
import de.btu.openinfra.backend.exception.OpenInfraWebException;

/**
 * This class supplies methods to create primer objects and to request
 * primer classes using a singleton implementation.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class PojoPrimer {

    /**
     * Pojo primer used to create primer objects and to request primer classes.
     */
    private static final PojoPrimer pojoPrimer = new PojoPrimer();

    /**
     * Hash map of OpenInfraPojo subclasses.
     */
    private Map<OpenInfraSchemas,
        Map<String, Class<? extends OpenInfraPojo>>> pojoClasses;

    /**
     * Default non-argument constructor. The constructor initiates
     * the hash map.
     */
    private PojoPrimer() {
        initiatePojoClasses();
    }

    /**
     * Creates a primer object for the given schema and pojo class.
     * @param schema the schema
     * @param projectId the project id
     * @param locale A Java.util locale objects.
     * @param pojoClass the pojo class
     * @return primer object if pojoClass and the schema exist in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    public static OpenInfraPojo primePojoStatically(
            OpenInfraSchemas schema,
            UUID projectId,
            Locale locale,
            String pojoClass) {
        switch(schema) {
            case SYSTEM:
            case PROJECTS:
                return pojoPrimer.primePojo(
                        schema,
                        new PtLocaleDao(projectId, schema).read(locale),
                        pojoClass);
            default:
                return pojoPrimer.primePojo(schema, null, pojoClass);
        }

    }

    /**
     * Returns all primer class names for the given schema.
     * @param schema the schema
     * @return a list of all primer class names if schema exists in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    public static List<String> getPrimerNamesStatically(
            OpenInfraSchemas schema) {
        return pojoPrimer.getPrimerNames(schema);
    }

    /**
     * This methods initiates the hash map of OpenInfraPojo subclasses.
     */
    private void initiatePojoClasses() {
        pojoClasses = new HashMap<OpenInfraSchemas,
                Map<String, Class<? extends OpenInfraPojo>>>();

        // add meta pojo classes
        pojoClasses.put(
                OpenInfraSchemas.META_DATA,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        addClassesForSchema(
                OpenInfraSchemas.META_DATA,
                findAllClasses("de.btu.openinfra.backend.db.pojos.meta"));

        // add rbac pojo classes
        pojoClasses.put(
                OpenInfraSchemas.RBAC,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        addClassesForSchema(
                OpenInfraSchemas.RBAC,
                findAllClasses("de.btu.openinfra.backend.db.pojos.rbac"));

        // add SYSTEM pojo classes
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().addUrls(ClasspathHelper.forPackage(
                        "de.btu.openinfra.backend.db.pojos")).
                        filterInputsBy(new FilterBuilder().
                        include("de\\.btu\\.openinfra\\.backend\\.db\\.pojos\\"
                                + "..*\\.class").
                        exclude("de\\.btu\\.openinfra\\.backend\\.db\\.pojos\\"
                                + "..*\\..*\\.class")).
                        setScanners(
                                new SubTypesScanner(false),
                                new ResourcesScanner(),
                                new TypeElementsScanner()));
        pojoClasses.put(
                OpenInfraSchemas.SYSTEM,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        addClassesForSchema(
                OpenInfraSchemas.SYSTEM,
                findAllClasses(reflections));

        // add projects pojo classes
        pojoClasses.put(
                OpenInfraSchemas.PROJECTS,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        pojoClasses.get(OpenInfraSchemas.PROJECTS).putAll(
                pojoClasses.get(OpenInfraSchemas.SYSTEM));
        addClassesForSchema(
                OpenInfraSchemas.PROJECTS,
                findAllClasses("de.btu.openinfra.backend.db.pojos.project"));

    }

    /**
     * This method finds all classes for the given package name.
     * @param packageName the package name
     * @return list of all classes for the given package name
     */
    private List<Class<? extends OpenInfraPojo>> findAllClasses(
            String packageName) {
        Reflections reflections = new Reflections(
                packageName,
                new SubTypesScanner(false),
                new ResourcesScanner(),
                new TypeElementsScanner());

        return findAllClasses(reflections);
    }

    /**
     * This method finds all classes for the given reflections object.
     * @param reflections the reflections object
     * @return list of all classes for the given reflections object
     */
    private List<Class<? extends OpenInfraPojo>> findAllClasses(
            Reflections reflections) {
        // get all class names
        Set<String> classNames = reflections.getStore().getStoreMap().get(
                "TypeElementsScanner").keySet();

        // return the list of classes
        return ReflectionUtils.forNames(classNames, reflections
              .getConfiguration().getClassLoaders());
    }

    /**
     * This method adds for the given schema all given classes to the hash map
     * (except interfaces and abstract classes).
     * @param schema the schema
     * @param classes the classes
     */
    private void addClassesForSchema(
            OpenInfraSchemas schema,
            List<Class<? extends OpenInfraPojo> > classes) {
        for(Class<? extends OpenInfraPojo> currentClass : classes) {
            if(Modifier.isAbstract(currentClass.getModifiers()) == false &&
                    currentClass.isInterface() == false) {
                pojoClasses.get(schema).put(
                        currentClass.getSimpleName().replaceAll(
                                "Pojo", "").toLowerCase(),
                        currentClass);
            }
        }
    }

    /**
     * This method checks if the given pojo class is a subclass of PtLocalePojo.
     * If it is true then the method initiates the pojo object with the given
     * PtLocale object.
     * @param pojo the pojo object
     * @param pojoClass the pojo class
     * @param locale a PtLocale object
     */
    private void checkAndInitiatePtLocale(
            OpenInfraPojo pojo,
            Class<?> pojoClass,
            PtLocale locale) {
        // check if the pojo class is a subclass of PtLocalePojo
        if(PtLocalePojo.class.isAssignableFrom(pojoClass) == true) {
            // initiate the pojo object with the given PtLocale object.
            PtLocalePojo ptLocalPojo = (PtLocalePojo) pojo;
            ptLocalPojo.setCharacterCode(
                    locale.getCharacterCode().getCharacterCode());
            ptLocalPojo.setCountryCode(
                    locale.getCountryCode().getCountryCode());
            ptLocalPojo.setLanguageCode(
                    locale.getLanguageCode().getLanguageCode());
        }
    }

    /**
     * This method converts the pojo object into a primer object.
     * A primer object is an empty but structured pojo object. The primer
     * object represents the structure of the pojo object without content.
     * @param pojo the pojo object
     * @param pojoClass the pojo class
     * @param locale a PtLocale object
     */
    private void makePrimer(
            OpenInfraPojo pojo,
            Class<?> pojoClass,
            PtLocale locale) {

        // initiate the pojo object with the given PtLocale object if
        // the pojo class is a subclass of PtLocalePojo
        checkAndInitiatePtLocale(pojo, pojoClass, locale);

        for(Method method : pojoClass.getMethods()) {
            // for each setter method with exactly one parameter
            if(method.getName().startsWith("set") &&
                    void.class.isAssignableFrom(method.getReturnType()) &&
                    method.getParameterTypes().length == 1) {

                // if the method parameter is a subclass of OpenInfraPojo
                if(OpenInfraPojo.class.isAssignableFrom(
                        method.getParameterTypes()[0]) == true) {
                    // create a new pojo, invoke the set method and call
                    // makePrimer for the new pojo
                    try {
                        OpenInfraPojo newPojo = (OpenInfraPojo)
                                method.getParameterTypes()[0].newInstance();
                        method.invoke(pojo, newPojo);
                        makePrimer(newPojo, newPojo.getClass(), locale);
                    } catch (IllegalAccessException |
                            IllegalArgumentException |
                            InvocationTargetException |
                            InstantiationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                // if the method parameter is a subclass of List
                if(List.class.isAssignableFrom(
                        method.getParameterTypes()[0]) == true) {
                    // and if the list has a generic parameter
                    if(method.getGenericParameterTypes().length > 0) {
                        // get the class of the generic parameter
                        Type type = method.getGenericParameterTypes()[0];
                        ParameterizedType pType = (ParameterizedType) type;
                        Class<?> genericParameterClass =
                                (Class<?>) pType.getActualTypeArguments()[0];
                        // if the class of the generic parameter is a subclass
                        // of LocalizedString
                        if(LocalizedString.class.isAssignableFrom(
                                genericParameterClass) == true) {
                            // create a new list of LocalizedString
                            List<LocalizedString> list =
                                    new ArrayList<LocalizedString>();
                            // add one LocalizedString
                            list.add(new LocalizedString());
                            // create and set the PtLocalePojo of the
                            // LocalizedString
                            list.get(0).setLocale(new PtLocalePojo());
                            // call makePrimer for the PtLocalePojo
                            makePrimer(
                                    list.get(0).getLocale(),
                                    list.get(0).getLocale().getClass(),
                                    locale);
                            try {
                                method.invoke(pojo, list);
                            } catch (IllegalAccessException |
                                    IllegalArgumentException |
                                    InvocationTargetException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a primer object for the given schema and pojo class.
     * @param schema the schema
     * @param locale a PtLocale object
     * @param pojoClass the pojo class
     * @return primer object if pojoClass and the schema exist in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    private OpenInfraPojo primePojo(
            OpenInfraSchemas schema,
            PtLocale locale,
            String pojoClass) {
        try {
            OpenInfraPojo pojo =
                    pojoClasses.get(schema).get(pojoClass).newInstance();
            makePrimer(pojo, pojo.getClass(), locale);
            return pojo;
        } catch (InstantiationException | IllegalAccessException |
                NullPointerException e) {
            throw new OpenInfraWebException(e);
        }
    }

    /**
     * Returns all primer class names for the given schema.
     * @param schema the schema
     * @return a list of all primer class names if schema exists in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    private List<String> getPrimerNames(OpenInfraSchemas schema) {
        try {
            return new ArrayList<String>(pojoClasses.get(schema).keySet());
        }
        catch(NullPointerException e) {
            throw new OpenInfraWebException(e);
        }
    }
}
