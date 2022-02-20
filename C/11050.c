#include <stdio.h>
int my_factorial(int a){
    if(a>0)
        return a*my_factorial(a-1);
    else return 1;
}
int main(){
    int N,K;
    scanf("%d %d",&N,&K);
    printf("%d\n",my_factorial(N)/(my_factorial(N-K)*my_factorial(K)));
    return 0;
}