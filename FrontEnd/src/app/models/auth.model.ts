export interface SignUpRequest {
  username: string;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  Adresse:string;
}

export interface LoginRequest {
  username: string;
  password: string;
}