import type { InbodyDetail } from '@/types/inbody.interface';

// 아래 계산식은 인터넷 상의 자료로 만든 간이식입니다.

// 적정 체중
// 남자: 키(m) ^ 2 * 22,
// 여자: 키(m) ^ 2 * 21
// https://www.diabetes.or.kr/general/dietary/dietary_02.php

// 평균 키
// 남자: 173.7cm
// 여자: 160.9cm
// https://sgsg.hankyung.com/article/2017062366731#:~:text=%EB%82%A8%20173.7%E3%8E%9D%20%EC%97%AC%20160.9%E3%8E%9D
const MALE_AVG_HEIGHT = 1.737;
const FEMALE_AVG_HEIGHT = 1.609;

// 적정 체지방률
// 남자 : 17%
// 여자 : 23%
// https://ko.wikipedia.org/wiki/%EC%B2%B4%EC%A7%80%EB%B0%A9_%EB%B9%84%EC%9C%A8#:~:text=4%20%EA%B0%99%EC%9D%B4%20%EB%B3%B4%EA%B8%B0-,%EC%A0%81%EC%A0%88%ED%95%9C%20%EC%B2%B4%EC%A7%80%EB%B0%A9%20%EB%B9%84%EC%9C%A8,%EB%86%92%EC%95%84%EC%A7%80%EB%8A%94%20%EB%8D%B0%20%EC%9B%90%EC%9D%B8%EC%9D%B4%20%EC%9E%88%EB%8B%A4.

// 적정 체지방량
// 남자 : 체중 * 17%
// 여자 : 체중 * 23&
// https://ko.wikipedia.org/wiki/%EC%B2%B4%EC%A7%80%EB%B0%A9_%EB%B9%84%EC%9C%A8#:~:text=4%20%EA%B0%99%EC%9D%B4%20%EB%B3%B4%EA%B8%B0-,%EC%A0%81%EC%A0%88%ED%95%9C%20%EC%B2%B4%EC%A7%80%EB%B0%A9%20%EB%B9%84%EC%9C%A8,%EB%86%92%EC%95%84%EC%A7%80%EB%8A%94%20%EB%8D%B0%20%EC%9B%90%EC%9D%B8%EC%9D%B4%20%EC%9E%88%EB%8B%A4.

// 적정 골격근량
// 남자 : 체중 * 35%
// 여자 : 체중 * 30%
// https://www.elecom.co.jp.k.gj.hp.transer.com/pickup/column/healthcare_column/00021/

// 적정 BMI
// 정상 최저: 18.5
// 정상 최고: 23
// https://health.seoulmc.or.kr/healthCareInfo/myBMIPopup.do

// 적정 인바디값
export function getAverageValue(inbody: InbodyDetail, sex: number) {
    const height = inbody.height
        ? inbody.height / 100
        : sex === 1
        ? MALE_AVG_HEIGHT
        : FEMALE_AVG_HEIGHT;

    const weight = height * height * (sex === 1 ? 22 : 21);

    const percentBodyFat = sex === 1 ? 17 : 23;
    const minPercentBodyFat = sex === 1 ? 15 : 20;
    const maxPercentBodyFat = sex === 1 ? 18 : 25;
    const bodyFatMass = (inbody.weight * percentBodyFat) / 100;

    const percentSkeletalMuscle = sex === 1 ? 25 : 20;
    const skeletalMuscleMass = (inbody.weight * percentSkeletalMuscle) / 100;

    const minBodyMassIndex = 18.5;
    const maxBodyMassIndex = 23;

    return {
        weight,
        minPercentBodyFat,
        maxPercentBodyFat,
        percentBodyFat,
        bodyFatMass,
        skeletalMuscleMass,
        minBodyMassIndex,
        maxBodyMassIndex,
    };
}

// 그래프 상 인바디 최솟t값
export function getMinValue(inbody: InbodyDetail, sex: number) {
    const height = inbody.height
        ? inbody.height / 100
        : sex === 1
        ? MALE_AVG_HEIGHT
        : FEMALE_AVG_HEIGHT;

    const weight = height * height * (sex === 1 ? 11 : 10);

    const percentBodyFat = sex === 1 ? 1 : 3;
    const bodyFatMass = (inbody.weight * percentBodyFat) / 100;

    const percentSkeletalMuscle = sex === 1 ? 20 : 10;
    const skeletalMuscleMass = (inbody.weight * percentSkeletalMuscle) / 100;

    const bodyMassIndex = 10;

    return {
        weight: 20,
        percentBodyFat,
        bodyFatMass,
        skeletalMuscleMass,
        bodyMassIndex,
    };
}

// 그래프 상 인바디 최댓값
export function getMaxValue(inbody: InbodyDetail, sex: number) {
    const height = inbody.height
        ? inbody.height / 100
        : sex === 1
        ? MALE_AVG_HEIGHT
        : FEMALE_AVG_HEIGHT;

    const weight = height * height * (sex === 1 ? 40 : 38);

    const percentBodyFat = sex === 1 ? 40 : 50;
    const bodyFatMass = (inbody.weight * percentBodyFat) / 100;

    const percentSkeletalMuscle = sex === 1 ? 50 : 45;
    const skeletalMuscleMass = (inbody.weight * percentSkeletalMuscle) / 100;

    const bodyMassIndex = 40;

    return {
        weight: 40,
        percentBodyFat,
        bodyFatMass,
        skeletalMuscleMass,
        bodyMassIndex,
    };
}
