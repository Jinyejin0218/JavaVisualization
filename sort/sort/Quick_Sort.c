#include <stdio.h>

void quick_sort(int arr[], int arr_length) {
	for (int i = 0; i < arr_length; i++) {
		int temp = arr[0];
		if (arr[0] > arr[i]) {
			arr[0] = arr[i];
			arr[i] = temp;
		}
	}
}
void main() {
	int arr[5] = { 5,3,8,4,6 };

	int arr_length = sizeof(arr) / sizeof(int);		//�迭 ����

	//���� ��
	for (int i = 0; i < arr_length; i++) {
		printf("%d\t", arr[i]);
	}
	printf("\n");
	quick_sort(arr, arr_length);

	//���� ��
	for (int i = 0; i < arr_length; i++) {
		printf("%d\t", arr[i]);
	}
}