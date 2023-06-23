#include <jni.h>
#include <vector>
#include <unordered_set>
#include <unordered_map>
#include <string>

using namespace std;

/****** Variabili JNI ******/
jclass arrayListClass;
jclass integerClass;
jclass stringClass;

jmethodID arrayListInit;
jmethodID integerInit;

jmethodID getMethod;
jmethodID addMethod;
jmethodID sizeMethod;
jmethodID intValueMethod;

/****** Variabili Database ******/
struct Mostro {
    string Nome;
    string Descrizione;
    string Ambiente;
    string Categoria;
    string Taglia;
    float Sfida{};
    int PF{};
    int CA{};
    int FOR{};
    int DES{};
    int COST{};
    int INT{};
    int SAG{};
    int CAR{};
};

unordered_map<int, Mostro> ID;
unordered_map<string, unordered_map<string, unordered_set<int>>> Filtri;

/****** Funzioni C++ ******/

// NB: A causa di errori del compilatore non è stato possibile sfruttare i template per
//      evitare l'utilizzo delle funzioni addElement
[[maybe_unused]] void addElement(JNIEnv* env, vector<int>& cppList, jobject arrayListObject, int index) {
    cppList.push_back(env->CallIntMethod(env->CallObjectMethod(arrayListObject, getMethod, index), intValueMethod));
}
[[maybe_unused]] void addElement(JNIEnv* env, unordered_set<int>& cppList, jobject arrayListObject, int index) {
    cppList.insert(env->CallIntMethod(env->CallObjectMethod(arrayListObject, getMethod, index), intValueMethod));
}
[[maybe_unused]] void addElement(JNIEnv* env, vector<string>& cppList, jobject arrayListObject, int index) {
    cppList.emplace_back(env->GetStringUTFChars(reinterpret_cast<jstring>(env->CallObjectMethod(arrayListObject, getMethod, index)), reinterpret_cast<jboolean *>(false)));
}
[[maybe_unused]] void addElement(JNIEnv* env, unordered_set<string>& cppList, jobject arrayListObject, int index) {
    cppList.insert(env->GetStringUTFChars(reinterpret_cast<jstring>(env->CallObjectMethod(arrayListObject, getMethod, index)), reinterpret_cast<jboolean *>(false)));
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
            addElement(env, cppList[i], arrayListObject, j);
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
        convert2CPP(env, env->CallObjectMethod(List, getMethod, i), cppList[i]);

}

// NB: A causa di errori del compilatore non è stato possibile sfruttare i template per
//      evitare l'uso del doppione di convert2JNI
jobject convert2JNI(JNIEnv* env, unordered_set<int>& result) {

    jobject javaArrayList = env->NewObject(arrayListClass, arrayListInit);

    // Iterazione sul UnorderedSet
    for (int element : result)
        env->CallBooleanMethod(javaArrayList, addMethod, env->NewObject(integerClass, integerInit, element));

    return javaArrayList;

}

jobject convert2JNI(JNIEnv* env, vector<string>& result) {

    jobject javaArrayList = env->NewObject(arrayListClass, arrayListInit);

    // Iterazione sul Vector
    for (const string& element : result)
        env->CallBooleanMethod(javaArrayList, addMethod, env->NewStringUTF(element.c_str()));

    return javaArrayList;

}

template<class T>
jobject convert2JNI(JNIEnv* env, vector<vector<T>>& result) {

    jobject javaArrayList = env->NewObject(arrayListClass, arrayListInit);

    // Iterazione sul Vector
    for (vector<T>& element : result)
        env->CallBooleanMethod(javaArrayList, addMethod, convert2JNI(env, element));

    return javaArrayList;

}

template<class T>
bool intersect(const unordered_set<T>& set1, const unordered_set<T>& set2, unordered_set<T>& finalSet) {

    if (set1.size() < set2.size())
        return intersect(set2, set1, finalSet);

    if (set1.empty()) {
        finalSet = set2;
        return false;
    }
    if (set2.empty()) {
        finalSet = set1;
        return false;
    }

    for (const auto& id : set2)
        if (set1.count(id))
            finalSet.insert(id);

    return finalSet.empty();

}

void processTablesNativeCPP(const vector<unordered_set<int>>& cppFilterTableList, unordered_set<int>& result) {

    if (cppFilterTableList.empty()) {
        result = unordered_set<int>();
        return;
    }

    unordered_set<int> temp = cppFilterTableList[0];
    for (int i = 1; i < cppFilterTableList.size(); i++) {
        result.clear();
        if (intersect(temp, cppFilterTableList[i], result))
            break;
        temp = result;
    }

}

void unifyTablesNativeCPP(const vector<unordered_set<int>>& cppFilterTableList, unordered_set<int>& result) {

    if (cppFilterTableList.empty()) {
        result = unordered_set<int>();
        return;
    }

    for (const unordered_set<int>& cppArrayList : cppFilterTableList)
        if (!cppArrayList.empty())
            result.insert(cppArrayList.begin(), cppArrayList.end());

}

void getMostroCPP(const int& id, vector<string>& result) {
    result = vector<string>();
    Mostro m = ID[id];
    result.push_back(to_string(id));
    result.push_back(m.Nome);
    result.push_back(m.Descrizione);
    result.push_back(m.Ambiente);
    result.push_back(m.Categoria);
    result.push_back(m.Taglia);
    result.push_back(to_string(m.Sfida));
    result.push_back(to_string(m.PF));
    result.push_back(to_string(m.CA));
    result.push_back(to_string(m.FOR));
    result.push_back(to_string(m.DES));
    result.push_back(to_string(m.COST));
    result.push_back(to_string(m.INT));
    result.push_back(to_string(m.SAG));
    result.push_back(to_string(m.CAR));
}

string convertString(string text, bool remove) {
    // Converti la stringa in caratteri minuscoli
    transform(text.begin(), text.end(), text.begin(), [](unsigned char c) {
        return tolower(c);
    });

    if (remove) {
        // Rimuovi gli spazi extra all'inizio e alla fine della stringa
        text.erase(text.begin(), find_if(text.begin(), text.end(), [](int ch) {
            return !isspace(ch);
        }));

        text.erase(find_if(text.rbegin(), text.rend(), [](int ch) {
            return !isspace(ch);
        }).base(), text.end());
    }

    return text;
}

bool stringSimilarity(const string& text1, const string& text2, bool is1Proc) {

    string processedText1 = is1Proc ? text1 : convertString(text1, true);
    if (processedText1.empty())
        return true;
    string processedText2 = convertString(text2, false);

    // Converti processedText1 in un unordered_set<string>
    unordered_set<string> text1_HS;
    string parola;
    for (const char& lettera : text1) {
        if (lettera == ' ') {
            text1_HS.insert(parola);
            parola = string();
        }
        else
            parola += lettera;
    }
    text1_HS.insert(parola);

    for (const string& t : text1_HS)
        if (processedText2.find(t) == string::npos)
            return false;

    return true;
}

/****** Funzioni JNI ******/
extern "C" JNIEXPORT jint JNI_OnLoad(JavaVM* vm, [[maybe_unused]] void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK)
        return JNI_ERR;

    // Definizione oggetti Classe
    arrayListClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    integerClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/lang/Integer")));
    stringClass = reinterpret_cast<jclass>(env->NewGlobalRef(env->FindClass("java/lang/String")));

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

    processTablesNativeCPP(cppFilterTableList, result);

    return convert2JNI(env, result);
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_unifyTablesNative(JNIEnv* env, jobject /* this */, jobject filterTableList) {

    vector<unordered_set<int>> cppFilterTableList = vector<unordered_set<int>>();
    unordered_set<int> result = unordered_set<int>();

    convert2CPP(env, filterTableList, cppFilterTableList);

    unifyTablesNativeCPP(cppFilterTableList, result);

    return convert2JNI(env, result);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_customsearchlibrary_NativeLib_updateDatabaseNative(JNIEnv *env, jobject /* this */, jobject id, jobject filtri, jobject nomi_filtri) {

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
        m.Nome = tempID[i][1];
        m.Descrizione = tempID[i][2];
        m.Ambiente = tempID[i][3];
        m.Categoria = tempID[i][4];
        m.Taglia = tempID[i][5];
        m.Sfida = stof(tempID[i][6]);
        if (m.Sfida < 0)
            m.Sfida = (float) pow(2, m.Sfida);
        m.PF = stoi(tempID[i][7]);
        m.CA = stoi(tempID[i][8]);
        m.FOR = stoi(tempID[i][9]);
        m.DES = stoi(tempID[i][10]);
        m.COST = stoi(tempID[i][11]);
        m.INT = stoi(tempID[i][12]);
        m.SAG = stoi(tempID[i][13]);
        m.CAR = stoi(tempID[i][14]);
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

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_getMostro(JNIEnv *env, jobject /* this */, jobject identifier) {
    vector<string> mostro;

    if (env->IsInstanceOf(identifier, stringClass)) {
        string nome = env->GetStringUTFChars(reinterpret_cast<jstring>(identifier), reinterpret_cast<jboolean *>(false));
        for (int index = 0; index < ID.size(); index++)
            if (ID[index].Nome == nome) {
                getMostroCPP(index, mostro);
                break;
            }
    }
    else if (env->IsInstanceOf(identifier, integerClass))
        getMostroCPP(env->CallIntMethod(identifier, intValueMethod), mostro);

    return convert2JNI(env, mostro);
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_customsearchlibrary_NativeLib_execSearchNative(JNIEnv *env, jobject /* this */, jstring text, jobject filter_list) {

    string testo(env->GetStringUTFChars(text, reinterpret_cast<jboolean *>(false)));
    vector<vector<string>> listaFiltri;
    convert2CPP(env, filter_list, listaFiltri);

    // Recupero degli ID associati ad ogni Filtro
    // NB: Si sta supponendo che i filtri passati siano solo Ambiente, Categoria e Taglia (in questo ordine)
    vector<unordered_set<int>> filterTableList = vector<unordered_set<int>>(3);
    vector<vector<unordered_set<int>>> idFiltro = vector<vector<unordered_set<int>>>(3);

    if (!listaFiltri[0].empty())
        for (const string& filtro : listaFiltri[0])
            idFiltro[0].push_back(Filtri["Ambiente"][filtro]);
    else
        idFiltro[0].push_back(unordered_set<int>());

    if (!listaFiltri[1].empty())
        for (const string& filtro : listaFiltri[1])
            idFiltro[1].push_back(Filtri["Categoria"][filtro]);
    else
        idFiltro[1].push_back(unordered_set<int>());

    if (!listaFiltri[2].empty())
        for (const string& filtro : listaFiltri[2])
            idFiltro[2].push_back(Filtri["Taglia"][filtro]);
    else
        idFiltro[2].push_back(unordered_set<int>());

    // Applicazione della regola OR tra i filtri della stessa categoria
    for (int i = 0; i < 3; i++)
        unifyTablesNativeCPP(idFiltro[i], filterTableList[i]);

    // Applicazione della regola AND tra le categorie di filtro
    unordered_set<int> listaID = unordered_set<int>();
    processTablesNativeCPP(filterTableList, listaID);

    // Recupero dei mostri che corrispondono alle specifiche della ricerca
    vector<vector<string>> result;
    vector<string> mostro;
    string processedText = convertString(env->GetStringUTFChars(text, reinterpret_cast<jboolean *>(false)), true);
    if (listaID.empty())
        for (const auto& id : ID) {
            getMostroCPP(id.first, mostro);
            if (stringSimilarity(processedText, mostro[1], true))
                result.push_back(mostro);
        }
    else
        for (const int& id : listaID) {
            getMostroCPP(id, mostro);
            if (stringSimilarity(processedText, mostro[1], true))
                result.push_back(mostro);
        }

    return convert2JNI(env, result);
}