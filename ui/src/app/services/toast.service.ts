import { Injectable } from '@angular/core';
import { IndividualConfig, ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(private toast: ToastrService) { }

  toastParams: Partial<IndividualConfig> = {
    closeButton: true, 
    progressBar: true,
    positionClass: 'toast-bottom-right',
    timeOut: 5000,
    tapToDismiss: true
  }

  showToast(title: string, message: string, type: string){
    switch(type){
      case 'success':
        this.toast.success(message, title, this.toastParams);
        break;
      case 'error':
        this.toast.error(message, title, this.toastParams);
        break;
      case 'info': 
        this.toast.info(message, title, this.toastParams);
    }
  }

}
