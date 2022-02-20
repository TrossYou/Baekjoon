#include <stdio.h>
#include <stdlib.h>
typedef struct{
    int x;
    int y;
}coord;
int compare(const void* first,const void* second){
    coord *a = (coord*)first;
    coord *b = (coord*)second;

    if(a->y < b->y) return -1;
    else if(a->y > b->y) return 1;
    else{
        if(a->x < b->x) return -1;
        else if(a->x > b->x) return 1;
    }
    return 0;
}
int main(){
    int N;
    scanf("%d",&N);
    coord *p = (coord*)malloc(N*sizeof(coord));
    for(int i=0;i<N;i++) scanf("%d %d",&p[i].x,&p[i].y);
    qsort(p,N,sizeof(coord),compare);
    for(int i=0;i<N;i++) printf("%d %d\n",p[i].x,p[i].y);
    return 0;
}