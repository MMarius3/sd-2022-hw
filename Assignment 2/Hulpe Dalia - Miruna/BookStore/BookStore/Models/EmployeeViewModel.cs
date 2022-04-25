using System;
using System.Collections.Generic;

namespace BookStore.Models
{
    public class EmployeeViewModel
    {
        public List<BookViewModel> Books { get; set; }
        public string SearchValue { get; set; }
    }
}
