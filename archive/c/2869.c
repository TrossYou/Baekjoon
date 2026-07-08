#include <stdio.h>

int main(){
    int A,B,V,check;
    scanf("%d %d %d",&A,&B,&V);
    check = (V-A)%(A-B);
    if(!check) printf("%d\n",(V-A)/(A-B)+1);
    else printf("%d\n",(V-A)/(A-B)+2);
    return 0;
}