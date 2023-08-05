export interface GymSimple {
    id: number;
    name: string;
}

export interface Gym extends GymSimple {
    description: string;
}

export interface GymImage {
    id?: number;
    image: string | File;
}
