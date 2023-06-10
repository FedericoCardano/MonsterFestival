#include <jni.h>
#include <vector>
#include <unordered_set>
#include <unordered_map>
#include <string>

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

/****** Variabili Database ******/
struct Mostro {
    string Ambiente;
    string Categoria;
    string Taglia;
    float Sfida{};
    int PF{};
    int CA{};
    int FOF{};
    int DES{};
    int COST{};
    int INT{};
    int SAG{};
    int CAR{};
};

string databaseVersion = "1.0";
unordered_map<int, Mostro> ID;
unordered_map<string, unordered_map<string, unordered_set<int>>> Filtri;

/****** Funzioni C++ ******/
[[maybe_unused]] void addElement(vector<int>& cppList, int& element) {
    cppList.push_back(element);
}

[[maybe_unused]] void addElement(unordered_set<int>& cppList, int& element) {
    cppList.insert(element);
}

[[maybe_unused]] void addElement(vector<string>& cppList, string& element) {
    cppList.push_back(element);
}

[[maybe_unused]] void addElement(unordered_set<string>& cppList, string& element) {
    cppList.insert(element);
}

template<typename T, template<typename...> typename W>
void convert2CPP(JNIEnv* env, jobject List, vector<W<T>>& cppList) {

    jint listSize = env->CallIntMethod(List, sizeMethod);
    jint arrayListSize;

    cppList = vector<W<T>>(listSize);
    T element;

    // Iterazione sull'ArrayList
    jobject arrayListObject;
    for (jint i = 0; i < listSize; i++) {
        arrayListObject = env->CallObjectMethod(List, getMethod, i);
        arrayListSize = env->CallIntMethod(arrayListObject, sizeMethod);

        // Iterazione sul arrayList
        for (jint j = 0; j < arrayListSize; j++)
            addElement(cppList[i], element);
    }

    // Rilascia la variabile JNI dopo l'uso
    env->DeleteLocalRef(arrayListObject);

}

template<class T>
void convert2CPP(JNIEnv* env, jobject List, vector<vector<unordered_set<T>>>& cppList) {

    jint listSize = env->CallIntMethod(List, sizeMethod);

    cppList = vector<vector<unordered_set<T>>>(listSize);

    // Iterazione sull'ArrayList
    for (jint i = 0; i < listSize; i++)
        convert2CPP(env, env->CallObjectMethod(env->CallObjectMethod(List, getMethod, i), getMethod, i), cppList[i]);

}

jobject convert2JNI(JNIEnv* env, unordered_set<int>& result) {

    jobject javaArrayList = env->NewObject(arrayListClass, arrayListInit);

    // Iterazione sul UnorderedSet
    for (int element : result)
        env->CallBooleanMethod(javaArrayList, addMethod, env->NewObject(integerClass, integerInit, element));

    return javaArrayList;

}

template<class T>
bool intersect(const unordered_set<T>& set1, const unordered_set<T>& set2, unordered_set<T>& finalSet) {

    if (set1.size() < set2.size())
        return intersect(set2, set1, finalSet);

    for (const auto& id : set2)
        if (set1.count(id))
            finalSet.insert(id);

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

    vector<unordered_set<int>> cppFilterTableList;
    unordered_set<int> result;

    convert2CPP(env, filterTableList, cppFilterTableList);

    unordered_set<int> temp = cppFilterTableList[0];
    for (int i = 1; i < cppFilterTableList.size(); i++) {
        result.clear();
        if (intersect(temp, cppFilterTableList[i], result))
            break;
        temp = result;
    }

    return convert2JNI(env, result);
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_unifyTablesNative(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    vector<unordered_set<int>> cppFilterTableList = vector<unordered_set<int>>();
    unordered_set<int> result = unordered_set<int>();

    convert2CPP(env, filterTableList, cppFilterTableList);

    for (const unordered_set<int>& cppArrayList : cppFilterTableList)
        result.insert(cppArrayList.begin(), cppArrayList.end());

    return convert2JNI(env, result);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_customsearchlibrary_NativeLib_getVersion(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF(databaseVersion.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_customsearchlibrary_NativeLib_updateDatabase(JNIEnv *env, jobject /* this */, jobject id,
                                                              jobject filtri, jobject nomi_filtri) {

    ID.clear();
    Filtri.clear();
    vector<vector<string>> tempID;
    vector<vector<unordered_set<int>>> tempFiltri;
    vector<vector<string>> tempNomiFiltri;
    convert2CPP(env, id, tempID);
    convert2CPP(env, filtri, tempFiltri);
    convert2CPP(env, nomi_filtri, tempNomiFiltri);

    // Salva i Mostri
    Mostro m;
    for (int i = 0; i < tempID.size(); i++) {
        m.Ambiente = tempID[i][0];
        m.Categoria = tempID[i][1];
        m.Taglia = tempID[i][2];
        m.Sfida = stof(tempID[i][3]);
        if (m.Sfida < 0)
            m.Sfida = (float) pow(2, m.Sfida);
        m.PF = stoi(tempID[i][4]);
        m.CA = stoi(tempID[i][5]);
        m.FOF = stoi(tempID[i][6]);
        m.DES = stoi(tempID[i][7]);
        m.COST = stoi(tempID[i][8]);
        m.INT = stoi(tempID[i][9]);
        m.SAG = stoi(tempID[i][10]);
        m.CAR = stoi(tempID[i][11]);
        ID.insert(make_pair(i, m));
    }

    // Salva i Filtri
    for (int i = 0; i < tempFiltri.size(); i++) {
        unordered_map<string, unordered_set<int>> catFiltro;
        for (int j = 0; j < tempFiltri[i].size(); j++)
            catFiltro.insert(make_pair(tempNomiFiltri[i][j + 1], tempFiltri[i][j]));
        Filtri.insert(make_pair(tempNomiFiltri[i][0], catFiltro));
    }

}