import { Component, OnInit } from '@angular/core';
import { faEye } from '@fortawesome/free-solid-svg-icons';
import { HomeService } from '../../home.service';
import { NgbModalConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrls: ['./manager-home.component.scss'],
  providers: [NgbModalConfig, NgbModal]
})
export class ManagerHomeComponent implements OnInit {
  faEye = faEye;
  userReims:any = [];
  fileNames: string[] = [];
  fileStatus = {
    status:'',
    requestType:'',
    percent:0
  }

  
  constructor(private homeService :HomeService,
    config: NgbModalConfig, 
    private modalService: NgbModal) {
      config.backdrop = 'static';
    config.keyboard = false;
     }

  ngOnInit(): void {
    this.getAllUserReim();
    
  }

  getAllUserReim(){
    console.log("get called")
    this.homeService.getAllUserReim()
    .subscribe((data :any) => {
      console.log(data);
      this.userReims = data;
      
    })
    
  }

  onClick(){
    console.log("clicked")
  }

  open(content:any) {
    this.modalService.open(content);
  }

  approve(id:any){
    console.log(id);
    this.homeService.approveReim(id)
    .subscribe((data :any) => {
      console.log(data);
      this.userReims = data; 
    })
  }

  deny(id:any){
    this.homeService.denyReim(id)
    .subscribe((data :any) => {
      console.log(data);
      this.userReims = data; 
    })
  }

  StatusFilter(statusForm:NgForm){
    console.log(statusForm.value.status);
    this.homeService.filterByStatus(statusForm.value.status)
    .subscribe((data :any) => {
      console.log(data);
      this.userReims = data; 
    })
  }

  onDownloadFile(fileName:string){
    console.log("Filename" + fileName)
    this.homeService.fileDownloadByAdmin(fileName)
    .subscribe(
      (event) => {
        console.log(event);
        this.progressReport(event);
      },
      (err) => {
        console.log(err);
      }
    )
  }

progressReport(httpEvent : HttpEvent<Blob>){
    switch(httpEvent.type){
     case HttpEventType.DownloadProgress : 
     this.updateStatus(httpEvent.loaded,httpEvent.total, 'Uploading');
     break;
     case HttpEventType.ResponseHeader : 
     console.log('Header' + httpEvent);
     break;
     case HttpEventType.Response : 
     console.log("File name " +  httpEvent.headers.getAll);
     saveAs(
       new File([httpEvent.body], 
        "file",
        /* httpEvent.headers.get('File-Name'), */
        {type:`${httpEvent.headers.get('Content-Type')};charset=utf-8`}))
     break;
     default:
       console.log(httpEvent);
    }
}

  updateStatus(loaded: number, total: number | undefined, requestType: string) {
   this.fileStatus.status = 'progress'
   this.userReims.requestType = requestType;

  }
   
}
