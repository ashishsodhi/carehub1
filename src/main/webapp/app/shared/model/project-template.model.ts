export interface IProjectTemplate {
  id?: number;
  serviceId?: number;
  templateDescription?: string;
  templateCreationClass?: string;
  whenCreated?: string;
}

export const defaultValue: Readonly<IProjectTemplate> = {};
