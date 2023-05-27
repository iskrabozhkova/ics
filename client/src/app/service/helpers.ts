export function getImageDimensions(url: string | null): Promise<{ width: number, height: number }> {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.src = url!;
  
    img.onload = () => {
      const width = img.width;
      const height = img.height;
      resolve({ width, height });
    };

    img.onerror = (error) => {
      reject(error);
    };
  });
}
  
  