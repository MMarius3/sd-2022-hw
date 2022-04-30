using System;
using System.ComponentModel.DataAnnotations;

namespace BookStore.Models
{
    public class EditBookViewModel
    {
        public string OldBook { get; set; }
        public BookViewModel NewBook { get; set; }
    }
}
