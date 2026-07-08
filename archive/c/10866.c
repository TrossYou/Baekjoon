#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(){
    int N,num=0;
    scanf("%d",&N);
    int *p =malloc(N*sizeof(int));
    char tmp[11];
    for(int i=0;i<N;i++){
        scanf("%s",tmp);
        if(!strcmp(tmp,"push_front")){
            for(int j=num;j>0;j--) p[j] = p[j-1];
            scanf("%d",&p[0]);
            num++;
        }
        else if(!strcmp(tmp,"push_back")){
            scanf("%d",&p[num]);
            num++;
        }
        else if(!strcmp(tmp,"pop_front")){
            if(!num) printf("-1\n");
            else{
                printf("%d\n",p[0]);
                for(int j=1;j<num;j++) p[j-1] = p[j];
                num--;
            }
        }
        else if(!strcmp(tmp,"pop_back")){
            if(!num) printf("-1\n");
            else{
                printf("%d\n",p[num-1]);
                num--;
            }
        }
        else if(!strcmp(tmp,"size")) printf("%d\n",num);
        else if(!strcmp(tmp,"empty")){
            if(!num) printf("1\n");
            else printf("0\n");
        }
        else if(!strcmp(tmp,"front")){
            if(!num) printf("-1\n");
            else printf("%d\n",p[0]);
        }
        else if(!strcmp(tmp,"back")){
            if(!num) printf("-1\n");
            else printf("%d\n",p[num-1]);
        }
    }
    return 0;
}