using BookStore.Repository.Data;
using System;
using System.Collections.Generic;
using System.Text;

namespace BookStore.Repository.Models
{
    public class BookEntity: IEntity
    {
        public Guid Id { get; set; }
        public string Title { get; set; }
        public string Author { get; set; }
        public string Genre { get; set; }
        public int Quantity { get; set; }
        public double Price { get; set; }
    }
}
