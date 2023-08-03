export function getToday() {
    return new Date().toISOString().slice(0, 10);
}

export function getAYearAgo() {
    const oneYearAgo = new Date();
    oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);
    return oneYearAgo.toISOString().slice(0, 10);
}
