export function getToday() {
    return new Date().toISOString().slice(0, 10);
}

export function getThreeMonthsAgo() {
    const threeMonthsAgo = new Date();
    threeMonthsAgo.setMonth(threeMonthsAgo.getMonth() - 3);
    return threeMonthsAgo.toISOString().slice(0, 10);
}

export function getSixMonthsAgo() {
    const sixMonthsAgo = new Date();
    sixMonthsAgo.setMonth(sixMonthsAgo.getMonth() - 6);
    return sixMonthsAgo.toISOString().slice(0, 10);
}

export function getOneYearAgo() {
    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);
    return oneYearAgo.toISOString().slice(0, 10);
}
