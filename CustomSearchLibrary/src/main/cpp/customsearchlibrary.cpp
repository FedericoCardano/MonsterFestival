#include <jni.h>
#include <vector>
#include <unordered_set>

using namespace std;

/****** Variabili JNI ******/
jclass arrayListClass;
jclass integerClass;

jmethodID arrayListInit;
jmethodID integerInit;

jmethodID getMethod;
jmethodID addMethod;
jmethodID sizeMethod;
jmethodID intValueMethod;

/****** Variabili C++ ******/
vector<unordered_set<int>> cppFilterTableList;
unordered_set<int> result;

/****** Funzioni C++ ******/
void convert2CPP(JNIEnv* env, jobject filterTableList) {

    jint listSize = env->CallIntMethod(filterTableList, sizeMethod);
    jint arrayListSize;

    cppFilterTableList = vector<unordered_set<int>>(listSize);

    // Iterazione sull'ArrayList
    jobject arrayListObject;
    for (jint i = 0; i < listSize; i++) {
        arrayListObject = env->CallObjectMethod(filterTableList, getMethod, i);
        arrayListSize = env->CallIntMethod(arrayListObject, sizeMethod);

        // Iterazione sul arrayList
        for (jint j = 0; j < arrayListSize; j++)
            cppFilterTableList[i].insert(env->CallIntMethod(env->CallObjectMethod(arrayListObject, getMethod, j), intValueMethod));
    }
    // Rilascia la variabile JNI dopo l'uso
    env->DeleteLocalRef(arrayListObject);

}

jobject convert2JNI(JNIEnv* env) {

    jobject javaArrayList = env->NewObject(arrayListClass, arrayListInit);

    // Iterazione sul UnorderedSet
    for (int element : result)
        env->CallBooleanMethod(javaArrayList, addMethod, env->NewObject(integerClass, integerInit, element));

    return javaArrayList;

}

bool intersect(const unordered_set<int>& set1, const unordered_set<int>& set2, unordered_set<int>& finalSet) {

    if (set1.size() < set2.size())
        return intersect(set2, set1, finalSet);

    for (const int& ID : set2)
        if (set1.count(ID))
            finalSet.insert(ID);

    return finalSet.empty();

}

/****** Funzioni JNI ******/
extern "C" JNIEXPORT jint JNI_OnLoad(JavaVM* vm, [[maybe_unused]] void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
        return JNI_ERR;

    // Definizione oggetti Classe
    arrayListClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    integerClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/lang/Integer")));

    // Definizione metodi delle Classi
    arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");
    integerInit = env->GetMethodID(integerClass, "<init>", "(I)V");

    getMethod = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
    addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
    sizeMethod = env->GetMethodID(arrayListClass, "size", "()I");
    intValueMethod = env->GetMethodID(integerClass, "intValue", "()I");

    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_processTablesNative(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    convert2CPP(env, filterTableList);

    unordered_set<int> temp = cppFilterTableList[0];
    for (int i = 1; i < cppFilterTableList.size(); i++) {
        result.clear();
        if (intersect(temp, cppFilterTableList[i], result))
            break;
        temp = result;
    }

    return convert2JNI(env);
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_unifyTablesNative(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    convert2CPP(env, filterTableList);

    result.clear();
    for (const unordered_set<int>& cppArrayList : cppFilterTableList)
        result.insert(cppArrayList.begin(), cppArrayList.end());

    return convert2JNI(env);
}