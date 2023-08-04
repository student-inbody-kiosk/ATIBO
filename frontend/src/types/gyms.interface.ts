export interface GymSimple {
    id: number;
    name: string;
}

export interface Gym extends GymSimple {
    description: string;
}

export interface GymDetail extends Gym {
    imageSet: Image[];
}

export interface Image {
    id: number;
    image: string;
}
