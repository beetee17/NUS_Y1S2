def search(x, arr):
    def help(low, high):
        if low > high:
            return -1

        mid = (low + high) // 2

        if x < arr[mid]:
            return help(low, mid - 1)
        elif x > arr[mid]:
            return help(mid + 1, high)
        else:
            return mid

    return help(0, len(arr) - 1)


if __name__ == "__main__":
    arr = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
    print(search(2, arr))
