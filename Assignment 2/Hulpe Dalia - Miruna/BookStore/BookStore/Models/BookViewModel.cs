using System;
using System.ComponentModel.DataAnnotations;

namespace BookStore.Models
{
    public class BookViewModel
    {
        public Guid Id { get; set; }

        [Required(ErrorMessage = "Title is required.")]
        [StringLength(100)]
        public string Title { get; set; }

        [Required(ErrorMessage = "Author is required.")]
        [StringLength(100)]
        public string Author { get; set; }

        [Required(ErrorMessage = "Genre is required.")]
        [StringLength(100)]
        public string Genre { get; set; }

        [Required(ErrorMessage = "Quantity is required.")]
        [Range(0, 999)]
        public int Quantity { get; set; }

        [Required(ErrorMessage = "Price is required.")]
        [Range(0, 999.99)]
        public double Price { get; set; }
    }
}
