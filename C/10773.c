#include <stdio.h>
#include <stdlib.h>
void push(int *arr,int size, int key){
    arr[size] = key;
}
void pop(int *arr,int size){
    arr[size-1] = 0;
}

int main(){
    int k,tmp,size=0;
    scanf("%d",&k);
    int *p = malloc(k*sizeof(int));
    for(int i=0;i<k;i++){
        scanf("%d",&tmp);
        if(tmp){
            push(p,size,tmp);
            size++;
        }
        else {
            pop(p,size);
            size--;
        }
    }
    int sum=0;
    for(int i=0;i<size;i++) sum += p[i];
    printf("%d\n",sum);
    return 0;
}