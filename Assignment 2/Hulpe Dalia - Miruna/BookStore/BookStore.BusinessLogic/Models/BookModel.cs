using System;
using System.Collections.Generic;
using System.Text;

namespace BookStore.BusinessLogic.Models
{
    public class BookModel
    {
        public Guid Id { get; set; }
        public string Title { get; set; }
        public string Author { get; set; }
        public string Genre { get; set; }
        public int Quantity { get; set; }
        public double Price { get; set; }
    }
}
