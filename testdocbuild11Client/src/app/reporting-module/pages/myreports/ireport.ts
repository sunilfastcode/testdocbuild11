export interface IReport {
  id?: number;
  title: string;
  description?: string;
  reportType: string;
  ctype: string;
  query: any;
  reportWidth?: string;
  editable?: boolean;
  isAssignedByRole?: boolean;
  isPublished?: boolean;
  isRefreshed?: boolean;
  isResetted?: boolean;
  ownerSharingStatus?: boolean;
  recipientSharingStatus?: boolean;
  sharedWithMe?: boolean;
  sharedWithOthers?: boolean;
  userId?: number;
  userDescriptiveField?: number;
  ownerId?: number;
  ownerDescriptiveField?: string;
}
