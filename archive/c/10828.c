#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(){
    int N,size=0;
    scanf("%d",&N);
    int *p = malloc(N*sizeof(int));
    char tmp[6];
    for(int i=0;i<N;i++){
        scanf("%s",tmp);
        if(!strcmp(tmp,"push")){
            scanf("%d",&p[size]);
            size++;
        }
        else if(!strcmp(tmp,"pop")){
            if(p[size-1]>=1){
                printf("%d\n",p[size-1]);
                p[size-1] = -1;
                size--;
            }
            else
                printf("-1\n");
        }
        else if(!strcmp(tmp, "size"))
            printf("%d\n",size);
        else if(!strcmp(tmp,"empty")){
            if(size==0) printf("1\n");
            else printf("0\n");
        }
        else if(!strcmp(tmp,"top")){
            if(size==0) printf("-1\n");
            else printf("%d\n",p[size-1]);
        }
    }
    return 0;
}