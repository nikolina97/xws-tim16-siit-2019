export interface UserRegistrationDTO {
    firstName : string;
    lastName: string;
	universityName: string;
	universityCity: string;
	universityCountry: string;
	expertises: Array<String>;
	email: string;
	password: string;
	role: string;
}