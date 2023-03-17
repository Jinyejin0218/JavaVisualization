#include <stdio.h>

void selection_sort(int arr[], int arr_length) {
	for (int i = 0; i < arr_length - 1; i++) {
		for (int j = i + 1; j < arr_length; j++) {
			int temp = arr[i];
			if (arr[i] > arr[j]) {
				arr[j] = arr[i];
				arr[i] = temp;
			}
		}
	}
}

void main() {
	int arr[5] = { 5,3,8,4,6 };

	int arr_length = sizeof(arr) / sizeof(int);		//배열 길이
	//정렬 전
	for (int i = 0; i < arr_length; i++) {
		printf("%d\t", arr[i]);
	}
	printf("\n");
	selection_sort(arr, arr_length);
	
	//정렬 후
	for (int i = 0; i < arr_length; i++) {
		printf("%d\t", arr[i]);
	}
}