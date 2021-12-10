import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { faPlus, faEye } from '@fortawesome/free-solid-svg-icons';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { RegisterResponse } from 'src/app/register-response';
import { HomeService } from '../../home.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss']
})
export class UserHomeComponent implements OnInit {
  faCoffee = faPlus;
  faEye = faEye;
  closeResult = '';
  selectedFile!: File;
  successRes = '';

  userReims:any = [];

  constructor(private modalService: NgbModal,
    private homeService : HomeService) { }

  ngOnInit(): void {
    this.getUserReim();
  }


  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  onFileSelect(event : any){
    
    this.selectedFile = <File>event.target.files[0];
    console.log(this.selectedFile)
  }
  
  onSubmit(form:NgForm){
     const fd = new FormData();
     fd.append('reimbAmount', form.value.reimbAmount);
     fd.append('reimbType', form.value.reimbType);
     fd.append('reimbDescription', form.value.reimbDescription);
     fd.append('reimbReceipt', this.selectedFile);
     this.homeService.reimSubmit(fd)
     .subscribe((data:RegisterResponse) => {
       console.log(data);
       this.successRes = data.message;
     })
  }

  getUserReim(){
    console.log("get called")
    this.homeService.getUserReimb()
    .subscribe((data :any) => {
      this.userReims = data.reimbursement;
      console.log(this.userReims.id);
    })
    
  }

  StatusFilter(statusForm:NgForm){
    console.log(statusForm.value.status);
    this.homeService.filterByStatusUser(statusForm.value.status)
    .subscribe(
      (data :any) => {
      console.log(data);
      this.userReims = data; 
    },
    (error : HttpErrorResponse) => {
      console.log(error
      )
    });
    
  }

}
