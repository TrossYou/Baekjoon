#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(){
    int N,size=0;
    char tmp[6];
    scanf("%d",&N);
    int *p = malloc(N*sizeof(int));
    while(N--){
        scanf("%s",tmp);
        if(!strcmp(tmp,"push")){
           scanf("%d",&p[size]);
           size++;
       }
        else if(!strcmp(tmp,"pop")){
            if(size == 0) printf("-1\n");
            else{
                printf("%d\n",p[0]);
                for(int i=1;i<size;i++) p[i-1] = p[i];
                size--;
            }
        }
        else if(!strcmp(tmp,"size")) printf("%d\n",size);
        else if(!strcmp(tmp,"empty")){
            if(size == 0) printf("1\n");
            else printf("0\n");
        }
        else if(!strcmp(tmp,"front")){
            if(size == 0) printf("-1\n");
            else printf("%d\n",p[0]);
        }
        else if(!strcmp(tmp,"back")){
            if(size == 0) printf("-1\n");
            else printf("%d\n",p[size-1]);
        }
    }
    return 0; 
}