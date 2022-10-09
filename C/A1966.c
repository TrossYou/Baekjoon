#include <stdio.h>
#include <stdlib.h>

int main(){
    int t,n,m;
    scanf("%d",&t);
    for(int i=0;i<t;i++){
        scanf("%d %d",&n,&m);
        int *p = malloc(n*sizeof(int));
        for(int j=0;j<n;j++)scanf("%d",&p[j]);
        int check=0,count=1,max=0;
        while(1){
            max=0;
            for(int j=0;j<n;j++) if(p[j]>max) max = p[j]; //최대선정
            while(p[check]!=max) check = (check+1)%n;
            if(check==m) break;
            else{
                p[check]=0;
                count++;
                check = (check+1)%n;
            }
        }
        printf("%d\n",count);
        free(p);
    }
    return 0;
}