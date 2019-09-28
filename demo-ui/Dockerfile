FROM node:12.2.0-alpine

# Override the base log level (info).
ENV NPM_CONFIG_LOGLEVEL warn

# Copy all local files into the image.
COPY . .

# Build for production.
RUN npm install

CMD ["npm", "start"]
