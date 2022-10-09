#include <stdio.h>
#include <stdlib.h>
int cmp(const void* first,const void* second){
    return *(int*)first < *(int*)second ? -1 : (*(int*)first > *(int*)second ? 1 : 0);
}
int binary_search_tree(int *arr,int size,int key){
    int first = 0,second = size-1,mid;
    if(key > arr[second] || key < arr[first]) return 0;
    while(1){
        mid = (first+second)/2;
        if(key==arr[mid]||key==arr[first] || key==arr[second]) return 1;
        else{
            if(first == mid) return 0;
            else if(key < arr[mid]) second = mid;
            else if(key > arr[mid]) first = mid;
        }
    }
}
int main(){
    int N,M,tmp;
    scanf("%d",&N);
    int *p = malloc(N*sizeof(int));
    for(int i=0;i<N;i++) scanf("%d",&p[i]);
    scanf("%d",&M);
    int *q = malloc(M*sizeof(int));
    qsort(p,N,sizeof(int),cmp);
    for(int i=0;i<M;i++){
        scanf("%d",&tmp);
        q[i] = binary_search_tree(p,N,tmp);
    }
    for(int i=0;i<M;i++) printf("%d\n",q[i]);
    return 0;
}