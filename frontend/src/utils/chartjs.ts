import type { InbodyDetail } from '@/types/inbody.interface';

// vue-cahrt.js Line Graph
// https://stackblitz.com/edit/github-sts9co?file=src%2FApp.vue,src%2FchartConfig.ts
export function inbodyToLineData(inbodys: InbodyDetail[]) {
    // base dataset
    const labels = [];
    const datasets = {
        weight: {
            label: '체중',
            backgroundColor: 'rgba(240, 81, 81, 1)',
            // point
            pointRadius: 4,
            pointBackgroundColor: 'rgba(240, 81, 81, 1)',
            // point border
            pointBorderWidth: 5,
            pointHoverBorderWidth: 5,
            pointHoverBorderColor: 'rgba(240, 81, 81, 1)',
            // line
            borderWidth: 5,
            borderColor: 'rgba(240, 81, 81,  0.3)',
            data: [],
        },
        percentBodyFat: {
            label: '체지방률',
            backgroundColor: 'rgba(255, 199, 10, 1)',
            // point
            pointRadius: 4,
            pointBackgroundColor: 'rgba(255, 199, 10, 1)',
            // point border
            pointBorderWidth: 5,
            pointHoverBorderWidth: 5,
            pointHoverBorderColor: 'rgba(255, 199, 10, 1)',
            // line
            borderWidth: 5,
            borderColor: 'rgba(255, 199, 10,  0.3)',
            data: [],
        },
        skeletalMuscleMass: {
            label: '골격근량',
            backgroundColor: 'rgba(51, 200, 31, 1)',
            // point
            pointRadius: 4,
            pointBackgroundColor: 'rgba(51, 200, 21, 1)',
            // point border
            pointBorderWidth: 5,
            pointHoverBorderWidth: 5,
            pointHoverBorderColor: 'rgba(51, 200, 21, 1)',
            // line
            borderWidth: 5,
            borderColor: 'rgba(51, 200, 21,  0.3)',
            data: [],
        },
        score: {
            label: '인바디 점수',
            backgroundColor: 'rgba(121, 121, 238, 1)',
            // point
            pointRadius: 4,
            pointBackgroundColor: 'rgba(121, 121, 238, 1)',
            // point border
            pointBorderWidth: 5,
            pointHoverBorderWidth: 5,
            pointHoverBorderColor: 'rgba(121, 121, 238, 1)',
            // line
            borderWidth: 5,
            borderColor: 'rgba(121, 121, 238,  0.3)',
            data: [],
        },
    };

    // fill the base dataset
    for (const inbody of inbodys) {
        labels.push(inbody.testDate);
        datasets.weight.data.push(inbody.weight ? inbody.weight : 0);
        datasets.percentBodyFat.data.push(
            inbody.percentBodyFat ? inbody.percentBodyFat : 0
        );
        datasets.skeletalMuscleMass.data.push(
            inbody.skeletalMuscleMass ? inbody.skeletalMuscleMass : 0
        );
        datasets.score.data.push(inbody.score ? inbody.score : 0);
    }

    // postprocess to the actual dataset
    const weightData = {
        labels,
        backgroundColor: 'blue',
        datasets: [datasets.weight],
    };

    const percentBodyFatData = {
        labels,
        backgroundColor: 'yellow',
        datasets: [datasets.percentBodyFat],
    };

    const skeletalMuscleMassData = {
        labels,
        backgroundColor: 'green',
        datasets: [datasets.skeletalMuscleMass],
    };

    const scoreData = {
        labels,
        backgroundColor: 'red',
        datasets: [datasets.score],
    };

    return {
        weightData,
        percentBodyFatData,
        skeletalMuscleMassData,
        scoreData,
    };
}

export const lineOptions = {
    responsive: true,
    maintainAspectRatio: false,
    layout: {
        padding: 30,
    },
    animations: {
        tension: {
            duration: 800,
            easing: 'linear',
            from: 0.8,
            to: 0.4,
        },
    },
    plugins: {
        legend: {
            display: false,
        },
    },
};

// vue-cahrt.js Bar Graph
// https://stackblitz.com/edit/github-6fyf6f?file=src%2FApp.vue,src%2FchartConfig.ts
export function inbodyToBarData(inbody: InbodyDetail) {
    const weightData = {
        labels: [''],
        datasets: [
            {
                label: '체중',
                backgroundColor: 'rgba(151, 198, 74,1)',
                data: [inbody.weight],
            },
        ],
    };
    const skeletalMuscleMassData = {
        labels: [''],
        datasets: [
            {
                label: '골격근량',
                backgroundColor: 'rgba(151, 198, 74,1)',
                data: [inbody.skeletalMuscleMass],
            },
        ],
    };
    const bodyFatMassData = {
        labels: [''],
        datasets: [
            {
                label: '체지방량',
                backgroundColor: 'rgba(151, 198, 74,1)',
                data: [inbody.bodyFatMass],
            },
        ],
    };
    const bodyMassIndexData = {
        labels: [''],
        datasets: [
            {
                label: 'BMI',
                backgroundColor: 'rgba(151, 198, 74,1)',
                data: [inbody.bodyMassIndex],
            },
        ],
    };
    const percentBodyFatData = {
        labels: [''],
        datasets: [
            {
                label: '체지방률',
                backgroundColor: 'rgba(151, 198, 74,1)',
                data: [inbody.percentBodyFat],
            },
        ],
    };

    return {
        weightData,
        skeletalMuscleMassData,
        bodyFatMassData,
        bodyMassIndexData,
        percentBodyFatData,
    };
}

export const barOptions = {
    responsive: true,
    maintainAspectRatio: false,
    indexAxis: 'y',
    plugins: {
        legend: {
            display: false,
        },
    },
    scales: {
        x: {
            suggestedMin: 0,
            suggestedMax: 100,
        },
    },
};
