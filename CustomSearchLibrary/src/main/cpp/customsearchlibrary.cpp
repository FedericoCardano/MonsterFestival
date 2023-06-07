#include <jni.h>

jclass arrayListClass;
jclass hashSetClass;

jmethodID hashInit;
jmethodID addAllMethod;
jmethodID retainAllMethod;
jmethodID sizeMethod_AL;
jmethodID sizeMethod_HS;
jmethodID getMethod;

// Carica i metodi e le classi di Default all'inizializzazione dell'oggetto NaviteLib
extern "C" JNIEXPORT jint
JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
        return JNI_ERR;

    // Definizione oggetti Classe
    arrayListClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    hashSetClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/HashSet")));

    // Definizione metodi delle Classi
    hashInit = env->GetMethodID(hashSetClass, "<init>", "()V");
    getMethod = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
    sizeMethod_AL = env->GetMethodID(arrayListClass, "size", "()I");
    sizeMethod_HS = env->GetMethodID(hashSetClass, "size", "()I");
    addAllMethod = env->GetMethodID(hashSetClass, "addAll", "(Ljava/util/Collection;)Z");
    retainAllMethod = env->GetMethodID(hashSetClass, "retainAll", "(Ljava/util/Collection;)Z");

    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_processTablesNative(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    jobject result = env->NewObject(hashSetClass, hashInit);

    jint listSize = env->CallIntMethod(filterTableList, sizeMethod_AL);

    env->CallBooleanMethod(result, addAllMethod, env->CallObjectMethod(filterTableList, getMethod, 0));
    for (jint i = 1; i < listSize; i++) {
        env->CallBooleanMethod(result, retainAllMethod, env->CallObjectMethod(filterTableList, getMethod, i));
        if (env->CallIntMethod(result, sizeMethod_HS) == 0)
            break;
    }

    return result;

}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_unifyTablesNative(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    jobject result = env->NewObject(hashSetClass, hashInit);

    jint listSize = env->CallIntMethod(filterTableList, sizeMethod_AL);

    for (jint i = 0; i < listSize; i++)
        env->CallBooleanMethod(result, addAllMethod, env->CallObjectMethod(filterTableList, getMethod, i));

    return result;

}