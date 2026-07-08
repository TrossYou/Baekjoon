#include <stdio.h>
#include <string.h>
char str[101];
char cnt[102];

char *push(char arr[], int size,char tmp){
    arr[size+1] = tmp;
    return arr;
}
char *pop(char arr[], int size, char tmp){
    if((tmp == ')'&&arr[size]=='(')||(tmp == ']' && arr[size]=='[')){
        arr[size] = 'n';
    }
    else arr[0]='0'; //여는게 부족한 경우+순서가 다른 경우
    return arr;
}
int main(){
    int check=1,a=0,b=0,size;
    char tmp,trash;
    while(1){
        tmp = getchar();
        printf("tmp = %c ",tmp);
        if(tmp=='.') break;
        size = 0;
        cnt[0] = '1';
        cnt[1] = 'n';
        while(tmp!='.'){
            if(tmp == '('){
                push(cnt,size,'(');
                size++;
            } 
            else if(tmp == ')'){  
                pop(cnt,size,')');
                size--;
            }
            else if(tmp == '['){
                push(cnt,size,'[');
                size++;
            }
            else if(tmp == ']'){
                pop(cnt,size,']');
                size--;
            }
            tmp = getchar();
        }
        if(cnt[0]=='1' && cnt[1]=='n') printf("yes\n");
        else printf("no\n");
    }
    return 0;
}