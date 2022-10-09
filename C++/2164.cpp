// #include <iostream>

// using namespace std;

// int main(){
//     int n,first=0,next;
//     cin >> n;
//     int *p = new int[2*n];
//     next = n;
//     for(int i=0;i<n;i++) p[i]=i+1;
//     while(first!=next-2&&first!=next-1){
//         first++; //버리는 행동
//         p[next] = p[first]; //뒤로 옮겨
//         next++;
//         first++;
//     }
//     cout << p[next-1] << endl;
//     delete p;
//     return 0;
// }

#include <iostream>
#include <queue>

using namespace std;

int main(){
    int n;
    queue<int> q;

    cin >> n;

    for(int i=1;i<=n;i++) q.push(i);

    while(q.size() > 1){
        q.pop();
        q.push(q.front());
        q.pop();
    }

    cout << q.front() << endl;

    return 0;
}