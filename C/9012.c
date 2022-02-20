#include <stdio.h>
#include <string.h>
int main()
{
    int N, in,check=1;
    scanf("%d", &N);
    char str[51];
    while (N--)
    {
        check = 1;
        in = 0;
        scanf("%s", str);
        for (int i = 0; i < strlen(str); i++)
        {
            if (str[i] == '(')
                in++;
            else
            {
                if (in == 0){
                    printf("NO\n");
                    check = 0;
                    break;
                }
                else
                    in--;
            }
        }
        if(check){
            if (in == 0)
                printf("YES\n");
            else
                printf("NO\n");
        }
    }
    return 0;
}