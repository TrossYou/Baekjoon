#include <stdio.h>
#include <stdlib.h>
int cmp(const void * first,const void * second){
    return first < second ? -1 : (first == second ? 0 : 1);
}
int binary_list(int arr[],int size){
    
}
int main(){
    int N,M,tmp;
    scanf("%d",&N);
    int *p = malloc(N*sizeof(int));
    for(int i=0;i<N;i++) scanf("%d",&p[i]);
    qsort(p,N,sizeof(int),cmp);
    scanf("%d",&M);
    int *q = malloc(M*sizeof(int));
    for(int i=0;i<M;i++){
        scanf("%d",&tmp);
        if()
    }
}