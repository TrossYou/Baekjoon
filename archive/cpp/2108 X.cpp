#include <iostream>
#include <cstdlib>
using namespace std;

int compare(const void* a, const void* b){
    const int* x = (int*) a;
    const int* y = (int*) b;

    return (*x>*y)?1:((*x==*y)?0:-1);
}

int arithGeo(int *p,int n){
    int sum=0;
    for(int i=0;i<n;i++) sum += p[i];
    return sum/n>=0 ? (int)(sum/n) : (int)(sum/n)-1;
}

int mid(int *p,int n){
   qsort(p,n,sizeof(int),compare);
   return p[n/2];
}

int most(int *p,int n){
    int q[n][2];
    for(int i=0;i<n;i++){ 
        q[i][0]=0;
        q[i][1]=1;
    }
    int t=0,max=0;
    for(int i=1;i<n;i++){
        if(p[i]==p[i-1]){
            q[t][0]=p[i];
            q[t][1]++;
            if(p[i]!=p[i+1]){
                if(max < q[t][1]) max = q[t][1];
                t++;
            }
        }
    }
    for(int i=0;i<n;i++){
        if(q[i][1]==max){
            for(int j=i+1;j<n;j++){
                if(q[j][1]==max) return q[j][0];
            }
            return q[i][0];
        }
    }
    if(n==1) return p[0];
    else return p[1];
}

int range(int *p,int n){
    return p[n-1]-p[0];
}

int main(){
    int N;
    cin >> N;
    int *p = new int[N];
    for(int i=0;i<N;i++){
        cin >> p[i];
    }
    cout << arithGeo(p,N) << endl << mid(p,N) << "\n" << most(p,N) << "\n" << range(p,N)<<endl;
    return 0;
}