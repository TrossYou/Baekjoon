#include <stdio.h>
#include <stdlib.h>
int main(){
    int N,M,ind=-1,amount=0;
    scanf("%d",&N);
    int *p = malloc(N*sizeof(int));
    int *q = malloc(N*sizeof(int));
    for(int i=0;i<N;i++) p[i] = i+1;
    scanf("%d",&M);
    for(int i=0;i<N;i++){
        while(amount!=M){
            ind++;
            if(ind < N)
                if(p[ind]) amount++;
                else continue;
            else{
                ind = -1;
            }
        }
        q[i] = p[ind];
        p[ind] = 0;
        amount = 0;
    }
    printf("<");
    for(int i=0;i<N-1;i++) printf("%d, ",q[i]);
    printf("%d>",q[N-1]);
    return 0;
}