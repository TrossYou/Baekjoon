#include <stdio.h>
#include <string.h>
#include <stdlib.h>
typedef struct{
    char p[51];
    int str_size;
}str;
int compare(const void* str1,const void* str2){
    str *a = (str*)str1;
    str *b = (str*)str2;
    if(a->str_size < b->str_size) return -1;
    else if(a->str_size > b->str_size) return 1;
    else return strcmp(a->p,b->p);
}
int main(){
    int N;
    scanf("%d",&N);
    str *q = (str*)malloc(N*sizeof(str));
    for(int i=0;i<N;i++){
        char tmp[51];
        scanf("%s",tmp);
        strcpy(q[i].p,tmp);
        q[i].str_size = strlen(tmp);
    }
    qsort(q,N,sizeof(str),compare);
    for(int i=0;i<N;i++){
        if(i==0) printf("%s\n",q[i].p);
        else if(strcmp(q[i-1].p,q[i].p)) printf("%s\n",q[i].p); 
    } 
}