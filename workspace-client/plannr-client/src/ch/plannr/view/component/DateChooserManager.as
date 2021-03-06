///////////////////////////////////////////////////////////////////////////////
// Licensed Materials - Property of IBM
// 5724-Y31,5724-Z78
// © Copyright IBM Corporation 2007, 2010. All Rights Reserved.
//
// Note to U.S. Government Users Restricted Rights:
// Use, duplication or disclosure restricted by GSA ADP Schedule
// Contract with IBM Corp.
///////////////////////////////////////////////////////////////////////////////
package ch.plannr.view.component
{
  import ilog.calendar.Calendar;
  import ilog.calendar.CalendarEvent;
  import ilog.utils.GregorianCalendar;
  import ilog.utils.TimeUnit;
  
  import mx.controls.DateChooser;
  import mx.events.CalendarLayoutChangeEvent;
  import mx.events.DateChooserEvent;
  
  /**
   * Manages the synchronization between the Calendar and the DateChoosers.
   */  
  public class DateChooserManager
  {
        
    private var _calendar:Calendar;
    private var _dateChooser1:DateChooser;
    private var _dateChooser2:DateChooser;
    
    public function DateChooserManager(calendar:Calendar, dateChooser1:DateChooser, dateChooser2:DateChooser) {
      _calendar = calendar;
      
      _dateChooser1 = dateChooser1;
      _dateChooser2 = dateChooser2;
      
      _dateChooser1.selectedRanges = [{rangeStart:calendar.startDisplayedDate, rangeEnd: calendar.endDisplayedDate}];
      _dateChooser2.selectedRanges = [{rangeStart:calendar.startDisplayedDate, rangeEnd: calendar.endDisplayedDate}];
      
      _dateChooser1.displayedMonth = calendar.startDisplayedDate.month;
      _dateChooser1.displayedYear = calendar.startDisplayedDate.fullYear;      
        
      var nextYear:Boolean = calendar.startDisplayedDate.month == 11;
        
      if (nextYear) {
        _dateChooser2.displayedMonth = 0;        
        _dateChooser2.displayedYear =  calendar.startDisplayedDate.fullYear + 1;
      } else {
        _dateChooser2.displayedMonth = calendar.startDisplayedDate.month + 1;        
        _dateChooser2.displayedYear =  calendar.startDisplayedDate.fullYear;            
      }
            
      
      initListeners();   
    }
    
    private function initListeners():void {
      _calendar.addEventListener(CalendarEvent.VISIBLE_TIME_RANGE_CHANGE, visibleTimeRangeChanged);
      _dateChooser1.addEventListener(DateChooserEvent.SCROLL, dateChooserScrolled); 
      _dateChooser2.addEventListener(DateChooserEvent.SCROLL, dateChooserScrolled2);
      _dateChooser1.addEventListener(CalendarLayoutChangeEvent.CHANGE, dateChooserHandler);
      _dateChooser2.addEventListener(CalendarLayoutChangeEvent.CHANGE, dateChooserHandler);
    }
    
    /**
     * Listener of date range changes to synchronize the date chooser.
     */  
    private function visibleTimeRangeChanged(event:CalendarEvent):void {
      
      var s:Date = event.startDate;
      var e:Date = event.endDate;
      
      var ranges:Array = [{ rangeStart: s, rangeEnd: e }]; 
              
      _dateChooser1.selectedRanges = ranges;
      _dateChooser2.selectedRanges = ranges;        
      
      //The length of a range cannot exceed 42 days on 2 months
              
      if ((_dateChooser1.displayedMonth == s.month && _dateChooser1.displayedYear == s.fullYear) || 
          (_dateChooser2.displayedMonth == s.month && _dateChooser2.displayedYear == s.fullYear && 
           _dateChooser2.displayedMonth == e.month && _dateChooser2.displayedYear == e.fullYear)) {
        //noop
      } else {
        
        _dateChooser1.displayedMonth = event.startDate.month;
        _dateChooser1.displayedYear =  event.startDate.fullYear;
        
        var nextYear:Boolean = event.startDate.month == 11;
        
        if (nextYear) {
          _dateChooser2.displayedMonth = 0;        
          _dateChooser2.displayedYear =  event.startDate.fullYear + 1;
        } else {
          _dateChooser2.displayedMonth = event.startDate.month + 1;        
          _dateChooser2.displayedYear =  event.startDate.fullYear;            
        }                    
      }
                                       
    }
    
    /**
     * Listener of scrolled data chooser event.
     */   
    private function dateChooserScrolled2(event:DateChooserEvent):void {
      
      if (_dateChooser2.displayedMonth == 0) {
        _dateChooser1.displayedMonth = 11;
        _dateChooser1.displayedYear = _dateChooser2.displayedYear - 1;
      } else {
        _dateChooser1.displayedMonth = _dateChooser2.displayedMonth - 1;
        _dateChooser1.displayedYear = _dateChooser2.displayedYear;           
      }
      
      dateChooserScrolledImpl();       
    }
    
    /**
     * Listener of scrolled data chooser event.
     */
    private function dateChooserScrolled(event:DateChooserEvent):void {
      if (_dateChooser1.displayedMonth == 11) {
        _dateChooser2.displayedMonth = 0;
        _dateChooser2.displayedYear = _dateChooser1.displayedYear + 1  
      } else {       
        _dateChooser2.displayedMonth = _dateChooser1.displayedMonth + 1;
        _dateChooser2.displayedYear = _dateChooser1.displayedYear;
      }        
      dateChooserScrolledImpl();
    }
    
    /**
     * Listener of scrolled data chooser event.
     */
    private function dateChooserScrolledImpl():void {
                     
      var calendar:GregorianCalendar = _calendar.calendar;
      
      //if the calendar is in date mode
      _calendar.date = null;
      
      switch (_calendar.mode) {
        case Calendar.DAY_MODE:
          //show the first day if the month
          _calendar.startDate = new Date(_dateChooser1.displayedYear, _dateChooser1.displayedMonth);
          _calendar.endDate = _calendar.startDate;           
          break;
        case Calendar.WEEK_MODE:
          //show the a range from the first day of month and that lasts the current duration.
          var d:Number = _calendar.endDisplayedDate.time - _calendar.startDisplayedDate.time;
          _calendar.startDate = new Date(_dateChooser1.displayedYear, _dateChooser1.displayedMonth);
          _calendar.endDate = new Date(_calendar.startDate.time + d);           
          break;
        case Calendar.MONTH_MODE:
          //show the current month
          _calendar.startDate = new Date(_dateChooser1.displayedYear, _dateChooser1.displayedMonth);
          //compute the last day of the month
          var date:Date = calendar.addUnits(_calendar.startDate, TimeUnit.MONTH, 1);
          date = calendar.addUnits(date, TimeUnit.DAY, -1, true); 
          _calendar.endDate = date;            
          break;
      }
    }
    
    /**
     * Listener of time range selection on a data chooser.
     */         
    private function dateChooserHandler(e:CalendarLayoutChangeEvent):void {
      var dch:DateChooser = e.currentTarget as DateChooser;
      var dc2:DateChooser = dch == _dateChooser1 ? _dateChooser2 : _dateChooser1;
      
      dc2.selectedRanges = [];
      
      var r:Object = dch.selectedRanges[0];
      if (r == null) {
        return;
      }
      
      if (r.rangeStart.time == r.rangeEnd.time) {
        if (e.newDate >= _calendar.startDisplayedDate && 
            e.newDate <= _calendar.endDisplayedDate) {     
          _calendar.date = null;           
          _calendar.startDate = _calendar.endDate = e.newDate;    
        } else {
          if (_calendar.date == null) {
            var d:int = _calendar.calendar.getDays(_calendar.startDisplayedDate, _calendar.endDisplayedDate);
            _calendar.startDate = e.newDate;
            _calendar.endDate = _calendar.calendar.addUnits(e.newDate, TimeUnit.DAY, d);
          } else {
            _calendar.date = e.newDate;;
          }
        }          
      } else {                       
        _calendar.date = null;
        _calendar.startDate = r.rangeStart;
        _calendar.endDate = r.rangeEnd;
      }
                             
    }          


  }
}
