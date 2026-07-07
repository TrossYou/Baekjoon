#include <stdio.h>

int main(){
    int T,H,W,N;
    scanf("%d",&T);
    while(T--){
        scanf("%d %d %d",&H,&W,&N);
        int p = N/H; //
        int q = N%H; //0
        if(q==0) printf("%d\n",H*100+p);
        else printf("%d\n",q*100+p+1);
    }
    return 0;
}