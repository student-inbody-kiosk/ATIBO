// 시작 ~ 끝 날짜를 기준으로 일수를 반환하는 함수
export function calculateDays(startDate: string, endDate: string) {
    const start = new Date(startDate);
    const end = new Date(endDate);

    const timeDifference = end.getTime() - start.getTime();

    const oneDayInMs = 24 * 60 * 60 * 1000;
    const daysDifference = Math.round(timeDifference / oneDayInMs);

    return daysDifference + 1;
}

// 시작 ~ 끝 날짜를 기준으로 인바디 테이블 생성
export function createIndexTable(startDate: string, endDate: string) {
    const start = new Date(startDate);
    const end = new Date(endDate);

    const indexTable: { [date: string]: number } = {};
    const currentDate = new Date(start);
    let index = 1;

    // 인덱스 테이블 생성 ('-'로 초기화)
    while (currentDate <= end) {
        const formattedDate = currentDate.toISOString().slice(0, 10);
        indexTable[formattedDate] = index - 1;
        currentDate.setDate(currentDate.getDate() + 1);
        index++;
    }

    return indexTable;
}
