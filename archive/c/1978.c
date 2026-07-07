#include <stdio.h>
int calculate(int a){
    if(a==1) return 0;
    for(int i=2;i<a;i++){
        if(a%i == 0) return 0;
    }
    return 1;
}
int main(){
    int N,cou=0,tmp;
    scanf("%d",&N);
    for(int i=0;i<N;i++){
        scanf("%d",&tmp);
        if(calculate(tmp)) cou++;
    }
    printf("%d\n",cou);
    return 0;
}