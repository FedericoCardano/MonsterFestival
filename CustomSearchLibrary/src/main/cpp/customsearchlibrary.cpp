#include <jni.h>

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_processTables(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    /****** Inizializzazione Variabile Result  ******/

    // Ottieni la classe dell'oggetto HashSet
    jclass hashSetClass = env->FindClass("java/util/HashSet");

    // Inizializza il Costruttore del HashSet e crea la variabile
    jobject result = env->NewObject(hashSetClass, env->GetMethodID(hashSetClass, "<init>", "()V"));

    // Ottieni l'ID del metodo add, remove e size nella classe HashList
    jmethodID addAllMethod = env->GetMethodID(hashSetClass, "addAll", "(Ljava/util/Collection;)Z");
    jmethodID removeAllMethod = env->GetMethodID(hashSetClass, "removeAll", "(Ljava/util/Collection;)Z");
    jmethodID sizeMethod = env->GetMethodID(hashSetClass, "size", "()I");

    /****** Definizione Variabile List ******/

    // Ottieni la classe dell'oggetto List
    jclass listClass = env->GetObjectClass(filterTableList);

    // Ottieni l'ID del metodo size nella classe List e ottieni la dimensione della lista
    jint listSize = env->CallIntMethod(filterTableList, env->GetMethodID(listClass, "size", "()I"));

    // Ottieni l'ID del metodo 'get' nella classe List
    jmethodID getMethod = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");

    /****** Applicazione Filtri ******/

    jobject hashSet;
    for (jint i = 0; i < listSize; i++) {
        hashSet = env->CallObjectMethod(filterTableList, getMethod, i);
        env->CallBooleanMethod(result, (i > 0 ? removeAllMethod : addAllMethod), hashSet);

        if (env->CallIntMethod(result, sizeMethod) == 0)
            break;
    }
    // Release della referenza locale all'HashSet
    env->DeleteLocalRef(hashSet);

    // Ritorna il risultato
    return result;

}