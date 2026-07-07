// #include <iostream>
// using namespace std;

// int realSize=0;
// class Node{
// public:
//     int node[2];
// };

// class NodeArr{
// private:
//     Node* nodeArr;
// public:
//     NodeArr(int n);
//     void myPush(int size,int key);
//     void myPrint(int key);
// };

// NodeArr::NodeArr(int n){
//     nodeArr = new Node[n];
// }

// void NodeArr::myPush(int size,int key){
//     if(size==0){
//         this->nodeArr[0].node[0] = key;
//         this->nodeArr[0].node[1] = 1;
//         realSize++;
//         return;
//     }
//     for(int i=0;i<size;i++){
//         if(this->nodeArr[i].node[0]==key){
//             this->nodeArr[i].node[1]++;
//             break;
//         }
//         else if(i==size-1){
//             this->nodeArr[size].node[0] = key;
//             this->nodeArr[size].node[1]++;
//             realSize++;
//         }
//     }
// }

// void NodeArr::myPrint(int key){
//     for(int i=0;i<realSize;i++){
//         if(this->nodeArr[i].node[0]==key){  //있다면
//             cout << this->nodeArr[i].node[1] << " ";
//             break;
//         }
//         else if(i==realSize-1){
//             cout << "0 ";
//         }
//     }
// }


// int main(){
//     int n,tmp;
//     cin >> n;
//     NodeArr p(n);
//     for(int i=0;i<n;i++){  //수 입력
//         cin >> tmp;
//         p.myPush(realSize,tmp);
//     }
//     int m;
//     cin >> m;
//     for(int i=0;i<m;i++){
//         cin >> tmp;
//         p.myPrint(tmp);
//     }
//     cout <<endl;
//     return 0;
// }

// #include <iostream>
// #include <vector>
// #include <algorithm>

// using namespace std;

// vector<int> a,b;
// int n,m,x;

// int find(int key){
//     int start = 0;
//     int end = a.size()-1;
//     int mid;
//     while(end - start >= 0){
//         mid = (start+end)/2;
//         if(a[mid] == key){
//             int i = 1;
//             int count =1;
//             while(a[mid+i]==a[mid]){
//                 count++;
//                 i++;
//             }
//             i-1;
//             while(a[mid+i]==a[mid]){
//                 count++;
//                 i--;
//             }
//             return count;
//         }
//         if(a[mid] > key) end = mid-1;
//         if(a[mid]<key) start = mid+1;
//     }
//     return 0;
// }

// int main(){
//     cin >> n;
//     for(int i=0;i<n;i++){
//         cin >> x;
//         a.push_back(x);
//     }
//     cin >> m;
//     for(int i=0;i<m;i++){
//         cin >> x;
//         b.push_back(x);
//     }
//     sort(a.begin(),a.end());
//     for(int i=0;i<b.size();i++){
//         cout << find(b[i]) << " ";
//     }
//     cout << endl;
//     return 0;
// }

#include <iostream>
#include <vector>
